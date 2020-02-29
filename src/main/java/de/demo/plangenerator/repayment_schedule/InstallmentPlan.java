package de.demo.plangenerator.repayment_schedule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@ApiModel(description = "Calculated monthly installment of the repayment")
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

    @ApiModelProperty(value = "Monthly installment to pay")
    public BigDecimal borrowerPaymentAmount;

    @ApiModelProperty(value = "Due date of installment")
    public ZonedDateTime date;

    @ApiModelProperty(value = "Initial outstanding principal")
    public BigDecimal initialOutstandingPrincipal;

    @ApiModelProperty(value = "Monthly interest value")
    public BigDecimal interest;

    @ApiModelProperty(value = "Monthly principal value")
    public BigDecimal principal;

    @ApiModelProperty(value = "Remaining outstanding principal")
    public BigDecimal remainingOutstandingPrincipal;
}
