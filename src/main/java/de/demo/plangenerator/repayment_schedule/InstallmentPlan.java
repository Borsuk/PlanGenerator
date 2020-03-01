package de.demo.plangenerator.repayment_schedule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@ApiModel(description = "Calculated monthly installment of the repayment")
public class InstallmentPlan {

    public InstallmentPlan(
            final ZonedDateTime date,
            final BigDecimal borrowerPaymentAmount,
            final BigDecimal principal,
            final BigDecimal interest,
            final BigDecimal initialOutstandingPrincipal,
            final BigDecimal remainingOutstandingPrincipal) {
        this.date = date;
        this.borrowerPaymentAmount = borrowerPaymentAmount;
        this.principal = principal;
        this.interest = interest;
        this.initialOutstandingPrincipal = initialOutstandingPrincipal;
        this.remainingOutstandingPrincipal = remainingOutstandingPrincipal;
    }

    @ApiModelProperty(value = "Monthly installment to pay")
    public final BigDecimal borrowerPaymentAmount;

    @ApiModelProperty(value = "Due date of installment")
    public final ZonedDateTime date;

    @ApiModelProperty(value = "Initial outstanding principal")
    public final BigDecimal initialOutstandingPrincipal;

    @ApiModelProperty(value = "Monthly interest value")
    public final BigDecimal interest;

    @ApiModelProperty(value = "Monthly principal value")
    public final BigDecimal principal;

    @ApiModelProperty(value = "Remaining outstanding principal")
    public final BigDecimal remainingOutstandingPrincipal;
}
