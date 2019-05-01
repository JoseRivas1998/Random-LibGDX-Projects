//
// Created by JoseR on 4/22/2019.
//

#ifndef COMPLEX_NUMBERS_C_COMPLEXNUMBER_H
#define COMPLEX_NUMBERS_C_COMPLEXNUMBER_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>


typedef struct ComplexNumber COMPLEX_NUMBER;

COMPLEX_NUMBER *new_ComplexNumber(double real, double complex);
COMPLEX_NUMBER *copy_ComplexNumber(COMPLEX_NUMBER *src);
void destroy_ComplexNumber(COMPLEX_NUMBER *self);

double real__complex(COMPLEX_NUMBER *self);
double complex_complex(COMPLEX_NUMBER *self);

double abs_complex(COMPLEX_NUMBER *self);

COMPLEX_NUMBER *scl_complex(COMPLEX_NUMBER *self, double scale);
COMPLEX_NUMBER *neg_complex(COMPLEX_NUMBER *self);
COMPLEX_NUMBER *con_complex(COMPLEX_NUMBER *self);

COMPLEX_NUMBER *add_complex(COMPLEX_NUMBER *operand1, COMPLEX_NUMBER *operand2);
COMPLEX_NUMBER *sub_complex(COMPLEX_NUMBER *operand1, COMPLEX_NUMBER *operand2);
COMPLEX_NUMBER *mul_complex(COMPLEX_NUMBER *operand1, COMPLEX_NUMBER *operand2);
COMPLEX_NUMBER *div_complex(COMPLEX_NUMBER *operand1, COMPLEX_NUMBER *operand2);

char *toString(COMPLEX_NUMBER *self);

#endif //COMPLEX_NUMBERS_C_COMPLEXNUMBER_H
