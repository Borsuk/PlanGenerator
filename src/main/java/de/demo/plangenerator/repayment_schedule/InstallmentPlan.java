package de.demo.plangenerator.repayment_schedule;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class InstallmentPlan {

    BigDecimal borrowerPaymentAmount;
    ZonedDateTime date;
    BigDecimal initialOutstandingPrincipal;
    BigDecimal interest;
    BigDecimal principal;
    BigDecimal remainingOutstandingPrincipal;
}
