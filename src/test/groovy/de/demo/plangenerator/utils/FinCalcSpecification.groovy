package de.demo.plangenerator.utils

import spock.lang.Specification

class FinCalcSpecification extends Specification {

    def 'annuity calculation is correct for known sample data'() {
        expect:
            FinCalc.annuity(ratePerMonth, presentValue, numberOfPeriods) == expectedAnnuity

        where:
            ratePerMonth                | presentValue              | numberOfPeriods  || expectedAnnuity
            BigDecimal.valueOf(0.05)    | BigDecimal.valueOf(5000)  | 24               || BigDecimal.valueOf(219.36d)
    }
}
