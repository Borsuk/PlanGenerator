package de.demo.plangenerator.repayment_schedule;

import de.demo.plangenerator.utils.FinCalc;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RepaymentScheduleService {

    public List<InstallmentPlan> calculateRepaymentSchedule(
            BigDecimal loanAmount,
            BigDecimal annualNominalRate,
            Integer duration,
            ZonedDateTime startDate) {

        BigDecimal annuity = FinCalc.annuity(annualNominalRate, loanAmount, duration);

        return new ArrayList<>();
    }


}
