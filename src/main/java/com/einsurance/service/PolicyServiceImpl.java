package com.einsurance.service;

import com.einsurance.dto.policy.*;
import com.einsurance.entity.Commission;
import com.einsurance.entity.Customer;
import com.einsurance.entity.Policy;
import com.einsurance.entity.Scheme;
import com.einsurance.exception.ResourceNotFoundException;
import com.einsurance.mapper.PolicyMapper;
import com.einsurance.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
@Transactional
public class PolicyServiceImpl implements PolicyService {

    private final PolicyRepository policyRepository;
    private final CustomerRepository customerRepository;
    private final SchemeRepository schemeRepository;
    private final PaymentRepository paymentRepository;
    private final CommissionRepository commissionRepository;
    private final PolicyMapper policyMapper;

    public PolicyServiceImpl(PolicyRepository policyRepository,
                             CustomerRepository customerRepository,
                             SchemeRepository schemeRepository,
                             PaymentRepository paymentRepository,
                             CommissionRepository commissionRepository,
                             PolicyMapper policyMapper) {
        this.policyRepository = policyRepository;
        this.customerRepository = customerRepository;
        this.schemeRepository = schemeRepository;
        this.paymentRepository = paymentRepository;
        this.commissionRepository = commissionRepository;
        this.policyMapper = policyMapper;
    }

    @Override
    public PolicyResponse purchasePolicy(String customerUsername, PolicyPurchaseRequest request) {
        Customer customer = customerRepository.findByUsername(customerUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with username: " + customerUsername));

        Scheme scheme = schemeRepository.findById(request.getSchemeId())
                .orElseThrow(() -> new ResourceNotFoundException("Scheme not found with ID: " + request.getSchemeId()));

        LocalDate dateIssued = LocalDate.now();
        LocalDate lapseDate = dateIssued.plusYears(request.getMaturityPeriod());

        String details = (request.getPolicyDetails() != null && !request.getPolicyDetails().isBlank())
                ? request.getPolicyDetails()
                : "Policy enrolled under scheme: " + scheme.getSchemeName();

        Policy policy = Policy.builder()
                .customer(customer)
                .scheme(scheme)
                .policyDetails(details)
                .premium(request.getPremium())
                .dateIssued(dateIssued)
                .maturityPeriod(request.getMaturityPeriod())
                .policyLapseDate(lapseDate)
                .build();

        Policy savedPolicy = policyRepository.save(policy);

        // If customer has an agent, initialize a commission record (default 10% commission on policy)
        if (customer.getInsuranceAgent() != null) {
            BigDecimal commissionRate = BigDecimal.valueOf(0.10); // 10%
            BigDecimal commissionAmount = request.getPremium().multiply(commissionRate).setScale(2, RoundingMode.HALF_UP);

            Commission commission = Commission.builder()
                    .insuranceAgent(customer.getInsuranceAgent())
                    .policy(savedPolicy)
                    .commissionAmount(commissionAmount)
                    .build();

            commissionRepository.save(commission);
        }

        return policyMapper.toResponse(savedPolicy, BigDecimal.ZERO);
    }

    @Override
    public PolicyResponse createPolicy(PolicyRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + request.getCustomerId()));

        Scheme scheme = schemeRepository.findById(request.getSchemeId())
                .orElseThrow(() -> new ResourceNotFoundException("Scheme not found with ID: " + request.getSchemeId()));

        Policy policy = policyMapper.toEntity(request, customer, scheme);
        Policy saved = policyRepository.save(policy);

        if (customer.getInsuranceAgent() != null) {
            BigDecimal commissionRate = BigDecimal.valueOf(0.10);
            BigDecimal commissionAmount = request.getPremium().multiply(commissionRate).setScale(2, RoundingMode.HALF_UP);

            Commission commission = Commission.builder()
                    .insuranceAgent(customer.getInsuranceAgent())
                    .policy(saved)
                    .commissionAmount(commissionAmount)
                    .build();

            commissionRepository.save(commission);
        }

        return policyMapper.toResponse(saved, BigDecimal.ZERO);
    }

    @Override
    @Transactional(readOnly = true)
    public PolicyResponse getPolicyById(Integer policyId) {
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with ID: " + policyId));

        BigDecimal totalPaid = paymentRepository.sumAmountByPolicyId(policyId);
        return policyMapper.toResponse(policy, totalPaid);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PolicyResponse> getAllPolicies(Pageable pageable) {
        return policyRepository.findAll(pageable)
                .map(p -> policyMapper.toResponse(p, paymentRepository.sumAmountByPolicyId(p.getPolicyId())));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PolicyResponse> getPoliciesByCustomerId(Integer customerId, Pageable pageable) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer not found with ID: " + customerId);
        }
        return policyRepository.findByCustomer_CustomerId(customerId, pageable)
                .map(p -> policyMapper.toResponse(p, paymentRepository.sumAmountByPolicyId(p.getPolicyId())));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PolicyResponse> getMyPolicies(String customerUsername, Pageable pageable) {
        return policyRepository.findByCustomer_Username(customerUsername, pageable)
                .map(p -> policyMapper.toResponse(p, paymentRepository.sumAmountByPolicyId(p.getPolicyId())));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PolicyResponse> getPoliciesByAgentId(Integer agentId, Pageable pageable) {
        return policyRepository.findByCustomer_InsuranceAgent_AgentId(agentId, pageable)
                .map(p -> policyMapper.toResponse(p, paymentRepository.sumAmountByPolicyId(p.getPolicyId())));
    }

    @Override
    public PremiumCalculationResponse calculatePremium(PremiumCalculationRequest request) {
        BigDecimal sumAssured = request.getSumAssured();
        int years = request.getMaturityPeriodYears();
        int age = request.getAge();

        BigDecimal rateOfInterest = request.getRateOfInterest() != null
                ? request.getRateOfInterest()
                : BigDecimal.valueOf(7.5);

        // Age factor adjustment: +0.2% per year if age > 40
        BigDecimal ageFactor = BigDecimal.ZERO;
        if (age > 40) {
            ageFactor = BigDecimal.valueOf((age - 40) * 0.2);
        }
        BigDecimal effectiveRate = rateOfInterest.add(ageFactor);

        String frequency = request.getPaymentFrequency() != null ? request.getPaymentFrequency().toUpperCase() : "ANNUALLY";
        int paymentsPerYear;
        switch (frequency) {
            case "MONTHLY" -> paymentsPerYear = 12;
            case "QUARTERLY" -> paymentsPerYear = 4;
            case "HALF_YEARLY" -> paymentsPerYear = 2;
            default -> {
                frequency = "ANNUALLY";
                paymentsPerYear = 1;
            }
        }

        // Base annual installment = SumAssured / Years
        BigDecimal annualInstallment = sumAssured.divide(BigDecimal.valueOf(years), 2, RoundingMode.HALF_UP);
        BigDecimal installmentPremium = annualInstallment.divide(BigDecimal.valueOf(paymentsPerYear), 2, RoundingMode.HALF_UP);

        BigDecimal totalInvestment = installmentPremium.multiply(BigDecimal.valueOf((long) years * paymentsPerYear))
                .setScale(2, RoundingMode.HALF_UP);

        // Maturity amount = SumAssured + (SumAssured * effectiveRate * years / 100)
        BigDecimal interestEarned = sumAssured.multiply(effectiveRate)
                .multiply(BigDecimal.valueOf(years))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        BigDecimal maturityAmount = sumAssured.add(interestEarned);

        return PremiumCalculationResponse.builder()
                .sumAssured(sumAssured)
                .maturityPeriodYears(years)
                .age(age)
                .rateOfInterest(effectiveRate)
                .paymentFrequency(frequency)
                .calculatedPremium(installmentPremium)
                .totalInvestment(totalInvestment)
                .maturityAmount(maturityAmount)
                .formulaExplanation("Calculated based on Sum Assured: " + sumAssured + ", Duration: " + years + " years, Effective Interest Rate: " + effectiveRate + "% (" + frequency + ")")
                .build();
    }

    @Override
    public PolicyResponse updatePolicy(Integer policyId, PolicyRequest request) {
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with ID: " + policyId));

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + request.getCustomerId()));

        Scheme scheme = schemeRepository.findById(request.getSchemeId())
                .orElseThrow(() -> new ResourceNotFoundException("Scheme not found with ID: " + request.getSchemeId()));

        policy.setCustomer(customer);
        policy.setScheme(scheme);
        policy.setPolicyDetails(request.getPolicyDetails());
        policy.setPremium(request.getPremium());
        policy.setDateIssued(request.getDateIssued());
        policy.setMaturityPeriod(request.getMaturityPeriod());
        policy.setPolicyLapseDate(request.getPolicyLapseDate());

        Policy updated = policyRepository.save(policy);
        BigDecimal totalPaid = paymentRepository.sumAmountByPolicyId(policyId);
        return policyMapper.toResponse(updated, totalPaid);
    }

    @Override
    public void deletePolicy(Integer policyId) {
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with ID: " + policyId));
        policyRepository.delete(policy);
    }
}
