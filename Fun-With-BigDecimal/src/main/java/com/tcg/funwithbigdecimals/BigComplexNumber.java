package com.tcg.funwithbigdecimals;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

public final class BigComplexNumber {

    private final BigDecimal real;
    private final BigDecimal complex;

    private BigComplexNumber(BigDecimal real, BigDecimal complex) {
        this.real = new BigDecimal(real.toString());
        this.complex = new BigDecimal(complex.toString());
    }

    public static BigComplexNumber of(BigDecimal real, BigDecimal complex) {
        return new BigComplexNumber(real, complex);
    }

    public static BigComplexNumber ofReal(BigDecimal real) {
        return new BigComplexNumber(real, BigDecimal.ZERO);
    }

    public BigComplexNumber copy() {
        return new BigComplexNumber(this.real, this.complex);
    }

    public BigDecimal abs() {
        return real.multiply(real).add(complex.multiply(complex)).sqrt(MathContext.UNLIMITED);
    }

    public BigComplexNumber conjugate() {
        return new BigComplexNumber(this.real, this.complex.negate());
    }

    public boolean isReal() {
        return this.complex.compareTo(BigDecimal.ZERO) == 0;
    }

    public BigComplexNumber add(BigComplexNumber other) {
        BigDecimal newReal = this.real.add(other.real);
        BigDecimal newComplex = this.real.add(other.complex);
        return new BigComplexNumber(newReal, newComplex);
    }

    public BigComplexNumber sub(BigComplexNumber other) {
        BigDecimal newReal = this.real.subtract(other.real);
        BigDecimal newComplex = this.real.subtract(other.complex);
        return new BigComplexNumber(newReal, newComplex);
    }

    public BigComplexNumber mult(BigComplexNumber other) {
        BigDecimal newReal = this.real.multiply(other.real).subtract(this.complex.multiply(other.complex));
        BigDecimal newComplex = this.real.multiply(other.complex).add(this.complex.multiply(other.real));
        return new BigComplexNumber(newReal, newComplex);
    }

    public BigComplexNumber mult(BigDecimal scale) {
        BigDecimal newReal = this.real.multiply(scale);
        BigDecimal newComplex = this.complex.multiply(scale);
        return new BigComplexNumber(newReal, newComplex);
    }

    public BigComplexNumber divide(BigComplexNumber other) {
        BigComplexNumber num = this.mult(other.conjugate());
        BigComplexNumber denom = other.mult(other.conjugate());
        return num.divide(denom.abs());
    }

    public BigComplexNumber divide(BigDecimal scale) {
        BigDecimal newReal = this.real.divide(scale);
        BigDecimal newComplex = this.complex.divide(scale);
        return new BigComplexNumber(newReal, newComplex);
    }

    public BigComplexNumber square() {
        return this.mult(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BigComplexNumber that = (BigComplexNumber) o;
        return real.equals(that.real) &&
                complex.equals(that.complex);
    }

    public BigDecimal getReal() {
        return real;
    }

    public BigDecimal getComplex() {
        return complex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(real, complex);
    }

    public BigComplexNumber pow(int exp) {
        if(exp == 0) {
            return new BigComplexNumber(BigDecimal.ONE, BigDecimal.ZERO);
        }
        if(exp == 2) {
            return this.square();
        }
        if(exp % 2 == 0) {
            BigComplexNumber halfSq = this.pow(exp / 2);
            return halfSq.mult(halfSq);
        } else {
            return this.mult(pow(exp - 1));
        }
    }

}
