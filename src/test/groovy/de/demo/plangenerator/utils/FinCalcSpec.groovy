package de.demo.plangenerator.utils

import spock.lang.Specification
import spock.lang.Unroll

class FinCalcSpec extends Specification {

    @Unroll
    def 'annuity calculation is correct for known sample data'() {
        expect:
            FinCalc.annuity(annualNomianlRate, presentValue, numberOfPeriods) == expectedAnnuity

        where:
            annualNomianlRate           | presentValue              | numberOfPeriods  || expectedAnnuity
            BigDecimal.valueOf(0.05)    | BigDecimal.valueOf(5000)  | 24               || BigDecimal.valueOf(219.36d)
    }

    @Unroll
    def 'monthly interest is correct for known sample data'() {
        given:
            Integer daysInMonth = 30
            Integer daysInYear = 360

        expect:
            FinCalc.monthlyInterest(annualNominalRate, daysInMonth, daysInYear, initialOutstandingPrincipal) == expectedMontlyInterest

        where:
            annualNominalRate           | initialOutstandingPrincipal   || expectedMontlyInterest
            BigDecimal.valueOf(0.05)    | BigDecimal.valueOf(5000.0)    || BigDecimal.valueOf(20.83)
            BigDecimal.valueOf(0.05)    | BigDecimal.valueOf(4801.47)   || BigDecimal.valueOf(20.01)
            BigDecimal.valueOf(0.05)    | BigDecimal.valueOf(218.37)    || BigDecimal.valueOf(0.91)
    }

    @Unroll
    def 'monthly principal value is correct for known sample data'() {
        expect:
            FinCalc.monthlyPrincipal(currentAnnuity, monthlyInterest, initialOutstandingPrincipal) == expectedMonthlyPrincipal
        where:
            currentAnnuity              | monthlyInterest               | initialOutstandingPrincipal   || expectedMonthlyPrincipal
            BigDecimal.valueOf(219.36)  | BigDecimal.valueOf(20.83)     | BigDecimal.valueOf(5000.0)    || BigDecimal.valueOf(198.53)
            BigDecimal.valueOf(219.36)  | BigDecimal.valueOf(20.01)     | BigDecimal.valueOf(4801.47)   || BigDecimal.valueOf(199.35)
            BigDecimal.valueOf(219.28)  | BigDecimal.valueOf(0.91)      | BigDecimal.valueOf(218.37)    || BigDecimal.valueOf(218.37)
    }

    @Unroll
    def 'monthly principal value cant exceed initial outstanding principal, it assumes value of initial outstanding principal in such case'() {
        expect:
            FinCalc.monthlyPrincipal(currentAnnuity, monthlyInterest, initialOutstandingPrincipal) == expectedMonthlyPrincipal
        where:
            currentAnnuity              | monthlyInterest               | initialOutstandingPrincipal   || expectedMonthlyPrincipal
            BigDecimal.valueOf(100.0)   | BigDecimal.valueOf(70.0)      | BigDecimal.valueOf(20.0)      || BigDecimal.valueOf(20.0)
    }


}
