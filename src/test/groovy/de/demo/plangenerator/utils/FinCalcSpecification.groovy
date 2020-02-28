package de.demo.plangenerator.utils

import spock.lang.Specification
import spock.lang.Unroll

class FinCalcSpecification extends Specification {

    @Unroll
    def 'annuity calculation is correct for known sample data'() {
        expect:
            FinCalc.annuity(ratePerMonth, presentValue, numberOfPeriods) == expectedAnnuity

        where:
            ratePerMonth                | presentValue              | numberOfPeriods  || expectedAnnuity
            BigDecimal.valueOf(0.05)    | BigDecimal.valueOf(5000)  | 24               || BigDecimal.valueOf(219.36d)
    }

    @Unroll
    def 'monthly interest is correct for known sample data'() {
        given:
            Integer daysInMonth = 30
            Integer daysInYear = 360

        expect:
            FinCalc.monthlyInterest(annualNominalRate, daysInMonth, daysInYear, outstandingPrincipal) == expectedMontlyInterest

        where:
            annualNominalRate           | outstandingPrincipal          || expectedMontlyInterest
            BigDecimal.valueOf(0.05)    | BigDecimal.valueOf(5000.0)    || BigDecimal.valueOf(20.83)
            BigDecimal.valueOf(0.05)    | BigDecimal.valueOf(4801.47)   || BigDecimal.valueOf(20.01)
            BigDecimal.valueOf(0.05)    | BigDecimal.valueOf(218.37)    || BigDecimal.valueOf(0.91)

    }
}
