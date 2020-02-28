package de.demo.plangenerator.repayment_schedule;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RepaymentScheduleController {

    @ApiOperation(value = "Generates new Repayment Schedule, returns list of installments with financial details", notes= "TODO") // TODO doc
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "TODO", response = Object.class) // TODO message & response doc
            })
    @RequestMapping(value = "/generate-plan", method = RequestMethod.POST)
    public List<InstallmentPlan> generatePlan(@RequestBody @Valid GeneratePlanRequest request) {
        return new ArrayList<>();
    }
}