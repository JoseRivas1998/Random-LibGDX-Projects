package com.tcg.complexnumbers;

import java.util.Objects;

public class ComplexNumber {

    private final float real;
    private final float complex;

    private ComplexNumber(float real, float complex) {
        this.real = real;
        this.complex = complex;
    }

    private ComplexNumber(ComplexNumber number) {
        this.real = number.real;
        this.complex = number.complex;
    }

    private ComplexNumber(float a, boolean isReal) {
        if(isReal) {
            this.real = a;
            this.complex = 0f;
        } else {
            this.real = 0;
            this.complex = a;
        }
    }

    public static ComplexNumber of(float real, float complex) {
        return new ComplexNumber(real, complex);
    }

    public static ComplexNumber of(ComplexNumber complexNumber) {
        return new ComplexNumber(complexNumber);
    }

    public static ComplexNumber of(float a, boolean isReal) {
        return new ComplexNumber(a, isReal);
    }

    public ComplexNumber copy() {
        return new ComplexNumber(this.real, this.complex);
    }

    public ComplexNumber add(ComplexNumber complexNumber) {
        float newReal = this.real + complexNumber.real;
        float newComplex = this.complex + complexNumber.complex;
        return new ComplexNumber(newReal, newComplex);
    }

    public ComplexNumber minus(ComplexNumber complexNumber) {
        float newReal = this.real - complexNumber.real;
        float newComplex = this.complex - complexNumber.complex;
        return new ComplexNumber(newReal, newComplex);
    }

    public ComplexNumber mult(ComplexNumber complexNumber) {
        float newReal = (this.real * complexNumber.real) - (this.complex * complexNumber.complex);
        float newComplex = (this.real * complexNumber.complex) + (this.complex * complexNumber.real);
        return new ComplexNumber(newReal, newComplex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexNumber that = (ComplexNumber) o;
        return Float.compare(that.real, real) == 0 &&
                Float.compare(that.complex, complex) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(real, complex);
    }

    @Override
    public String toString() {
        if(Float.compare(real, 0) == 0) {
            if(Float.compare(complex, 0) == 0) {
                return "0";
            } else {
                return String.format("%fi", this.complex);
            }
        }
        if(Float.compare(complex, 0) == 0) {
            if(Float.compare(real, 0) == 0) {
                return "0";
            } else {
                return String.format("%f", this.real);
            }
        }
        return String.format(
                "%f%c%fi",
                this.real,
                (Float.compare(this.complex, 0) > 0) ? '+' : '-',
                Math.abs(this.complex)
        );
    }
}
