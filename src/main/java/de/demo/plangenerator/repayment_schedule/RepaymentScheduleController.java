package de.demo.plangenerator.repayment_schedule;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

/**
 * API for Repayment Schedule.
 */
@RestController
@Slf4j
public class RepaymentScheduleController {

    @Autowired
    public RepaymentScheduleController(final RepaymentScheduleService repaymentScheduleService) {
        this.repaymentScheduleService = repaymentScheduleService;
    }

    private RepaymentScheduleService repaymentScheduleService;

    @ApiOperation(value = "Generates new Repayment Schedule, returns list of installments with financial details", notes= "TODO") // TODO doc
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Lit of scheduled installments", response = InstallmentPlan.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad request, violations are reported with ServerWebInputException structure (Spring Web)")
    })
    @PostMapping(value = "/generate-plan")
    @ResponseStatus(HttpStatus.CREATED)
    public List<InstallmentPlan> generatePlan(@RequestBody @Valid final GeneratePlanRequest request) {
        log.info("[Start] Generate repayment schedule. Accepted request: {}", request);

        BigDecimal nominalRateAsFraction = request.nominalRate.divide(BigDecimal.valueOf(100), MathContext.DECIMAL64);
        List<InstallmentPlan> repaymentInstallments = repaymentScheduleService.calculateRepaymentSchedule(request.loanAmount, nominalRateAsFraction, request.duration, request.startDate);

        log.info("[Finish] Generate repayment schedule. Processed request: {}", request);
        return repaymentInstallments;
    }
}
