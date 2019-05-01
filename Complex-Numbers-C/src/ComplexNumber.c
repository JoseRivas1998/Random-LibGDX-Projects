//
// Created by JoseR on 4/22/2019.
//

#include "ComplexNumber.h"

typedef struct ComplexNumber {
    double real;
    double complex;
} COMPLEX_NUMBER;

void complexNumber_construct(COMPLEX_NUMBER *self, double real, double complex) {
    self->real = real;
    self->complex = complex;
}

COMPLEX_NUMBER *copy_ComplexNumber(COMPLEX_NUMBER *src) {
    COMPLEX_NUMBER *newComplexNumber = calloc(1, sizeof(COMPLEX_NUMBER));
    complexNumber_construct(newComplexNumber, src->real, src->complex);
    return newComplexNumber;
}

COMPLEX_NUMBER *new_ComplexNumber(double real, double complex) {
    COMPLEX_NUMBER *newComplexNumber = calloc(1, sizeof(COMPLEX_NUMBER));
    complexNumber_construct(newComplexNumber, real, complex);
    return newComplexNumber;
}

void destroy_ComplexNumber(COMPLEX_NUMBER *self) {
    if (self) {
        free(self);
    }
}

double real__complex(COMPLEX_NUMBER *self) {
    return self->real;
}

double complex_complex(COMPLEX_NUMBER *self) {
    return self->complex;
}

double abs_complex(COMPLEX_NUMBER *self) {
    return sqrt(self->real * self->real + self->complex * self->complex);
}

COMPLEX_NUMBER *scl_complex(COMPLEX_NUMBER *self, double scale) {
    return new_ComplexNumber(self->real * scale, self->complex * scale);
}

COMPLEX_NUMBER *neg_complex(COMPLEX_NUMBER *self) {
    return scl_complex(self, -1);
}

COMPLEX_NUMBER *con_complex(COMPLEX_NUMBER *self) {
    return new_ComplexNumber(self->real, -self->complex);
}

COMPLEX_NUMBER *add_complex(COMPLEX_NUMBER *operand1, COMPLEX_NUMBER *operand2) {
    return new_ComplexNumber(operand1->real + operand2->real, operand1->complex + operand2->complex);
}

COMPLEX_NUMBER *sub_complex(COMPLEX_NUMBER *operand1, COMPLEX_NUMBER *operand2) {
    COMPLEX_NUMBER *negation = neg_complex(operand2);
    COMPLEX_NUMBER *result = add_complex(operand1, negation);
    destroy_ComplexNumber(negation);
    return result;
}

COMPLEX_NUMBER *mul_complex(COMPLEX_NUMBER *operand1, COMPLEX_NUMBER *operand2) {
    double newReal = (operand1->real * operand2->real) - (operand1->complex * operand2->complex);
    double newComplex = (operand1->real * operand2->complex) + (operand1->complex * operand2->real);
    return new_ComplexNumber(newReal, newComplex);
}

COMPLEX_NUMBER *div_complex(COMPLEX_NUMBER *operand1, COMPLEX_NUMBER *operand2) {
    COMPLEX_NUMBER *conjugate = con_complex(operand2);
    COMPLEX_NUMBER *num = mul_complex(operand1, conjugate);
    COMPLEX_NUMBER *denom = mul_complex(operand2, conjugate);
    COMPLEX_NUMBER *result = scl_complex(num, 1 / abs_complex(denom));
    destroy_ComplexNumber(conjugate);
    destroy_ComplexNumber(num);
    destroy_ComplexNumber(denom);
    return result;
}

char *toString(COMPLEX_NUMBER *self) {
    char *result = calloc(128, sizeof(char));
    if (self->real == 0) {
        if (self->complex == 0) {
            strcpy(result, "0");
        } else {
            sprintf(result, "%lfi", self->complex);
        }
    } else if (self->complex == 0) {
        if (self->real == 0) {
            strcpy(result, "0");
        } else {
            sprintf(result, "%lf", self->real);
        }
    } else {
        double absComplex = fabs(self->complex);
        char sign = (self->complex > 0) ? '+' : '-';
        sprintf(result,
                "%lf%c%lfi",
                self->real,
                sign,
                absComplex);
    }
    return result;
}
