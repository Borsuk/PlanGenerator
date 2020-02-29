package de.demo.plangenerator.repayment_schedule;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

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
            @ApiResponse(code = 201, message = "TODO", response = Object.class) // TODO message & response doc
            })
    @RequestMapping(value = "/generate-plan", method = RequestMethod.POST)
    public List<InstallmentPlan> generatePlan(@RequestBody @Valid final GeneratePlanRequest request) {
        log.info("[Start] Generate repayment schedule. Accepted request: {}", request);

        BigDecimal nominalRateAsFraction = request.nominalRate.divide(BigDecimal.valueOf(100), MathContext.DECIMAL64);
        List<InstallmentPlan> repaymentInstallments = repaymentScheduleService.calculateRepaymentSchedule(request.loanAmount, nominalRateAsFraction, request.duration, request.startDate);

        log.info("[Finish] Generate repayment schedule. Processed request: {}", request);
        return repaymentInstallments;
    }
}
