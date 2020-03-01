package de.demo.plangenerator.repayment_schedule

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import spock.lang.Unroll

import java.time.ZonedDateTime

@SpringBootTest
class RepaymentScheduleServiceSpec extends Specification {

    @Autowired
    RepaymentScheduleService repaymentScheduleService

    @Unroll
    def 'calculated schedule has the same cardinality as duration of the loan - #duration months'() {
        given:
            BigDecimal loanAmount = BigDecimal.valueOf(5000)
            BigDecimal annualNominalRate = BigDecimal.valueOf(0.05)
            ZonedDateTime startDate = ZonedDateTime.now()

        when:
            def result = repaymentScheduleService.calculateRepaymentSchedule(loanAmount, annualNominalRate, duration, startDate)

        then:
            result.size() == expectedSizeOfResult

        where:
            duration    ||  expectedSizeOfResult
            1           ||  1
            2           ||  2
            10          ||  10
            24          ||  24
    }

    def 'illegal argument exception due to negative duration'() {
        given:
            BigDecimal loanAmount = BigDecimal.valueOf(5000)
            BigDecimal annualNominalRate = BigDecimal.valueOf(0.05)
            Integer duration = -1
            ZonedDateTime startDate = ZonedDateTime.now()

        when:
            repaymentScheduleService.calculateRepaymentSchedule(loanAmount, annualNominalRate, duration, startDate)

        then:
            thrown IllegalArgumentException
    }

}
