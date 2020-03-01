package de.demo.plangenerator.repayment_schedule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * Request to generate repayment plan.
 */
@Validated
@ApiModel(description = "Request to generate repayment plan")
@ToString
public class GeneratePlanRequest {

    public GeneratePlanRequest(final BigDecimal loanAmount, final BigDecimal nominalRate, final Integer duration, final ZonedDateTime startDate){
        this.loanAmount = loanAmount;
        this.nominalRate = nominalRate;
        this.duration = duration;
        this.startDate = startDate;
    }
    // TODO add customer validator that will compare loanAmount and nominalRare against externalized config settings

    @ApiModelProperty(value = "Total amount of the loan")
    @NotNull
    @Positive
    public final BigDecimal loanAmount;

    @ApiModelProperty(value = "Annual rate of the loan - percentage as an integer")
    @NotNull
    @PositiveOrZero
    @DecimalMax(value = "100")
    public final BigDecimal nominalRate;

    @ApiModelProperty(value = "Duration of the loan in months")
    @NotNull
    @Min(1)
    public final Integer duration;

    @ApiModelProperty(value = "Start date of the loan - ISO8601 with timezone")
    @NotNull
    public final ZonedDateTime startDate;
}
