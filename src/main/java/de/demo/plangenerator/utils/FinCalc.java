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
     * @param initialOutstandingPrincipal initial outstanding principal in current month
     * @return
     */
    public static BigDecimal monthlyInterest(BigDecimal annualNominalRate, Integer daysInMonth, Integer daysInYear, BigDecimal initialOutstandingPrincipal) {
        BigDecimal numerator = annualNominalRate.multiply(BigDecimal.valueOf(daysInMonth)).multiply(initialOutstandingPrincipal);
        BigDecimal denominator = BigDecimal.valueOf(daysInYear);

        BigDecimal interest = numerator.divide(denominator, 2, RoundingMode.HALF_EVEN);

        return interest;
    }

    /**
     * Current value of monthly principal.
     * Expressed as a currentAnnuity - monthlyInterest.
     * <p>
     * If the subtraction gives a negative result, then the initial outstanding principal is used instead. This situation can happen for the vary last installment.
     *
     * @param currentAnnuity
     * @param monthlyInterest
     * @param initialOutstandingPrincipal initial outstanding principal - is used as a fallback result if calculation gives negative result
     * @return
     */
    public static BigDecimal monthlyPrincipal(BigDecimal currentAnnuity, BigDecimal monthlyInterest, BigDecimal initialOutstandingPrincipal) {
        BigDecimal monthlyPrincipal = currentAnnuity.subtract(monthlyInterest);
        return (monthlyPrincipal.compareTo(BigDecimal.ZERO) < 0) ? initialOutstandingPrincipal : monthlyPrincipal;
    }
}
