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
     * @param initialOutstandingPrincipal aka outstanding value of the loan to repay
     * @param numberOfPeriods number of periods left
     * @return annuity computed for given inputs
     */
    public static BigDecimal annuity(BigDecimal annualNominalRate, BigDecimal initialOutstandingPrincipal, int numberOfPeriods) {
        BigDecimal ratePerMonth = annualNominalRate.divide(BigDecimal.valueOf(12), MathContext.DECIMAL64); // scale=16 , Rounding=HALF_EVEN <- seems to be enough and correct

        BigDecimal numerator = ratePerMonth.multiply(initialOutstandingPrincipal);

        BigDecimal pow = (BigDecimal.ONE.add(ratePerMonth)).pow(-numberOfPeriods, MathContext.DECIMAL64);
        BigDecimal denominator = BigDecimal.ONE.subtract(pow);

        BigDecimal annuity =  numerator.divide(denominator, 2, RoundingMode.HALF_EVEN);

        return annuity;
    }


    /**
     * Current value of monthly interest
     * @param annualNominalRate
     * @param daysInMonth
     * @param daysInYear
     * @param outstandingPrincipal
     * @return
     */
    public static BigDecimal monthlyInterest(BigDecimal annualNominalRate, Integer daysInMonth, Integer daysInYear, BigDecimal outstandingPrincipal) {
        BigDecimal numerator = annualNominalRate.multiply(BigDecimal.valueOf(daysInMonth)).multiply(outstandingPrincipal);
        BigDecimal denominator = BigDecimal.valueOf(daysInYear);

        BigDecimal interest = numerator.divide(denominator, 2, RoundingMode.HALF_EVEN);

        return interest;
    }
}
