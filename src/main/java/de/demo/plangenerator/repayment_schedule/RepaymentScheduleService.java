package de.demo.plangenerator.repayment_schedule;

import de.demo.plangenerator.utils.FinCalc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Orchestration of Repayment Schedule calculations.
 */
@Service
@Slf4j
public class RepaymentScheduleService {

    @Value("${repayment_schedule.days_in_month:30}")
    Integer daysInMonth;

    @Value("${repayment_schedule.days_in_year:360}")
    Integer daysInYear;

    /**
     * Calculate monthly schedule of repayment.
     *
     * @param loanAmount loan amount (principal amount)
     * @param annualNominalRate annual interest rate of the loan, percentage expressed as fraction
     * @param duration number of installments in months
     * @param startDate date of Disbursement/Payout (due date of first installment)
     * @return list of monthly installments with all financial details
     */
    public List<InstallmentPlan> calculateRepaymentSchedule(
            final BigDecimal loanAmount,
            final BigDecimal annualNominalRate,
            final Integer duration,
            final ZonedDateTime startDate) {

        log.info("[Start] Calculate repayment schedule. loanAmount: {}, annualNominalRate: {}, duration: {}, startDate: {}", loanAmount, annualNominalRate, duration, startDate);

        List<InstallmentPlan> installments = new ArrayList<>(duration);

        BigDecimal remainingOutstandingPrincipal = loanAmount;

        for (int installmentNo=0; installmentNo < duration; installmentNo++) {
            BigDecimal initialOutstandingPrincipal = remainingOutstandingPrincipal;

            BigDecimal annuity = FinCalc.annuity(annualNominalRate, loanAmount, duration);
            BigDecimal interest = FinCalc.monthlyInterest(annualNominalRate, daysInMonth, daysInYear, initialOutstandingPrincipal);
            BigDecimal principal = FinCalc.monthlyPrincipal(annuity, interest, initialOutstandingPrincipal);
            BigDecimal borrowerPaymentAmount = FinCalc.borrowerPaymentAmount(principal, interest);
            remainingOutstandingPrincipal = initialOutstandingPrincipal.subtract(principal);

            ZonedDateTime date = startDate.plus(Duration.ofDays(daysInMonth * installmentNo));
            InstallmentPlan currentInstallment = new InstallmentPlan(date, borrowerPaymentAmount, principal, interest, initialOutstandingPrincipal, remainingOutstandingPrincipal);

            log.debug("Repayment installment was calculated for period #{} (0-based), borrower payment amount is {} ", installmentNo, borrowerPaymentAmount);

            installments.add(currentInstallment);
        }

        log.info("[Finish] Calculate repayment schedule. loanAmount: {}, annualNominalRate: {}, duration: {}, startDate: {}", loanAmount, annualNominalRate, duration, startDate);
        return installments;
    }


}
