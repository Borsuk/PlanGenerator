package de.demo.plangenerator.repayment_schedule;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class InstallmentPlan {

    public InstallmentPlan() {
        super();
    }

    public InstallmentPlan(ZonedDateTime date, BigDecimal borrowerPaymentAmount, BigDecimal principal, BigDecimal interest, BigDecimal initialOutstandingPrincipal, BigDecimal remainingOutstandingPrincipal) {
        this.date = date;
        this.borrowerPaymentAmount = borrowerPaymentAmount;
        this.principal = principal;
        this.interest = interest;
        this.initialOutstandingPrincipal = initialOutstandingPrincipal;
        this.remainingOutstandingPrincipal = remainingOutstandingPrincipal;
    }

    public BigDecimal borrowerPaymentAmount;
    public ZonedDateTime date;
    public BigDecimal initialOutstandingPrincipal;
    public BigDecimal interest;
    public BigDecimal principal;
    public BigDecimal remainingOutstandingPrincipal;
}
