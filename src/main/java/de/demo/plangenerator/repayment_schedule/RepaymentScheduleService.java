package de.demo.plangenerator.repayment_schedule;

import de.demo.plangenerator.utils.FinCalc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RepaymentScheduleService {

    @Value("${repayment_schedule.days_in_month:30}")
    Integer daysInMonth;

    @Value("${repayment_schedule.days_in_year:360}")
    Integer daysInYear;

    public List<InstallmentPlan> calculateRepaymentSchedule(
            BigDecimal loanAmount,
            BigDecimal annualNominalRate,
            Integer duration,
            ZonedDateTime startDate) {

        List<InstallmentPlan> installments = new ArrayList<>(duration);

        BigDecimal remainingOutstandingPrincipal = loanAmount;

        for (int installmentNo=0; installmentNo < duration; installmentNo++) {
            BigDecimal initialOutstandingPrincipal = remainingOutstandingPrincipal;

            BigDecimal annuity = FinCalc.annuity(annualNominalRate, loanAmount, duration);
            BigDecimal interest = FinCalc.monthlyInterest(annualNominalRate, daysInMonth, daysInYear, initialOutstandingPrincipal);
            BigDecimal principal = FinCalc.monthlyPrincipal(annuity, interest, initialOutstandingPrincipal);
            BigDecimal borrowerPaymentAmount = FinCalc.borrowerPaymentAmount(principal, interest);
            remainingOutstandingPrincipal = initialOutstandingPrincipal.subtract(principal);

            //TODO prepare info on installment -- add it to the list
            ZonedDateTime date = startDate.plus(Duration.ofDays(30 * installmentNo));
            InstallmentPlan currentInstallment = new InstallmentPlan(date, borrowerPaymentAmount, principal, interest, initialOutstandingPrincipal, remainingOutstandingPrincipal);

            installments.add(currentInstallment);
        }

        return installments;
    }


}
