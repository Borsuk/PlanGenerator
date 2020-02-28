package de.demo.plangenerator.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Financial Calculations utilities
 */
public class FinCalc {

    /**
     * Calculates annuity value.
     *
     * @param annualNominalRate annual nominal rate expressed as fraction
     * @param presentValue outstanding value of the load to repay
     * @param numberOfPeriods number of periods left
     * @return annuity computed for given inputs
     */
    public static BigDecimal annuity(BigDecimal annualNominalRate, BigDecimal presentValue, int numberOfPeriods) {
        BigDecimal ratePerMonth = annualNominalRate.divide(BigDecimal.valueOf(12), MathContext.DECIMAL64); // scale=16 , Rounding=HALF_EVEN <- seems to be enough

        BigDecimal numerator = ratePerMonth.multiply(presentValue);

        BigDecimal pow = (BigDecimal.ONE.add(ratePerMonth)).pow(-numberOfPeriods, MathContext.DECIMAL64);
        BigDecimal denominator = BigDecimal.ONE.subtract(pow);

        BigDecimal annuity =  numerator.divide(denominator, 2, RoundingMode.HALF_EVEN);

        return annuity;
    }
}