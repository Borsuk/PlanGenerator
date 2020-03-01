package de.demo.plangenerator.repayment_schedule

import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.lang.Unroll

import java.time.ZonedDateTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest
class RepaymentScheduleControllerSpec extends Specification {

    @Autowired
    private MockMvc mockMvc

    @SpringBean
    RepaymentScheduleService repaymentScheduleService = Mock()

    @Autowired
    private ObjectMapper objectMapper

    @Unroll
    def 'request to generate Repayment Schedule fails with invalid loanAmount - #reasonToFail'(){
        given: 'invalid request object without loanAmount'
            GeneratePlanRequest request = new GeneratePlanRequest(
                    loanAmount,
                    BigDecimal.ONE,
                    24,
                    ZonedDateTime.now()
            )
        and: 'its text representation'
            String requestWithoutLoanAmount = objectMapper.writeValueAsString(request)

        expect:
            mockMvc.perform(
                    post('/generate-plan')
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestWithoutLoanAmount)
            )
                    .andExpect(status().isBadRequest())
        where:
            loanAmount                      |   reasonToFail
            null                            |   'is null'
            BigDecimal.valueOf(-1.0d)       |   'is negative'
            BigDecimal.ZERO                 |   'is zero'
    }

    @Unroll
    def 'request to generate Repayment Schedule fails with invalid nominalRate - #reasonToFail'(){
        given: 'invalid request object without nominalRate'
            GeneratePlanRequest request = new GeneratePlanRequest(
                    BigDecimal.valueOf(1000.0d),
                    nominalRate,
                    24,
                    ZonedDateTime.now()
            )
        and: 'its text representation'
            String requestWithoutLoanAmount = objectMapper.writeValueAsString(request)

        expect:
            mockMvc.perform(
                    post('/generate-plan')
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestWithoutLoanAmount)
            )
                    .andExpect(status().isBadRequest())
        where:
            nominalRate                     |   reasonToFail
            null                            |   'is null'
            BigDecimal.valueOf(-1.0d)       |   'is negative'
            BigDecimal.valueOf(101.0d)      |   'it\'s over 100 %'  // my assumption is that the rate for the loan is invalid if > 100%
    }

    @Unroll
    def 'request to generate Repayment Schedule fails with invalid duration - #reasonToFail'(){
        given: 'invalid request object without duration'
            GeneratePlanRequest request = new GeneratePlanRequest(
                    BigDecimal.valueOf(1000.0d),
                    BigDecimal.ONE,
                    duration,
                    ZonedDateTime.now()
            )
        and: 'its text representation'
            String requestWithoutLoanAmount = objectMapper.writeValueAsString(request)

        expect:
            mockMvc.perform(
                    post('/generate-plan')
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestWithoutLoanAmount)
            )
                    .andExpect(status().isBadRequest())
        where:
            duration    |   reasonToFail
            null        |   'is null'
            -1          |   'is negative'
            0           |   'is zero duration'
    }

    def 'request to generate Repayment Schedule is invalid without startDate'(){
        given: 'invalid request object without startDate'
            GeneratePlanRequest request = new GeneratePlanRequest(
                    BigDecimal.valueOf(1000.0d),
                    BigDecimal.ONE,
                    24,
                    null
            )
        and: 'its text representation'
            String requestWithoutLoanAmount = objectMapper.writeValueAsString(request)

        expect:
            mockMvc.perform(
                    post('/generate-plan')
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestWithoutLoanAmount)
            )
                    .andExpect(status().isBadRequest())
    }

}
