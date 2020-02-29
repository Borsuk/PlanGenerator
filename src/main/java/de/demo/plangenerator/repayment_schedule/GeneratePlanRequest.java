package de.demo.plangenerator.repayment_schedule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Validated
@ApiModel(description = "Request to fetch (already generated) article scores")
@ToString
public class GeneratePlanRequest {

    // TODO add customer validator that will compare loanAmount and nominalRare against externalized config settings

    @ApiModelProperty(value = "Total amount of the loan")
    @NotNull
    @Positive
    public BigDecimal loanAmount;

    @ApiModelProperty(value = "Annual rate of the loan - percentage as an integer")
    @NotNull
    @PositiveOrZero
    @DecimalMax(value = "100")
    public BigDecimal nominalRate;

    @ApiModelProperty(value = "Duration of the loan in months")
    @NotNull
    @Min(1)
    public Integer duration;

    @ApiModelProperty(value = "Start date of the loan - ISO8601 with timezone")
    @NotNull
    public ZonedDateTime startDate;
}
