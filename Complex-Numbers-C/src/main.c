#include <stdio.h>
#include <ComplexNumber.h>

double map(double value, double start1, double stop1, double start2, double stop2) {
    return start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1));
}

int mand(COMPLEX_NUMBER *z0, int max) {
    COMPLEX_NUMBER *maxAbsC = new_ComplexNumber(2, 2);
    double maxAbs = abs_complex(maxAbsC);
    destroy_ComplexNumber(maxAbsC);

    COMPLEX_NUMBER *z = copy_ComplexNumber(z0);
    for(int i = 0; i < max; i++) {
        if(abs_complex(z) > maxAbs * 2) {
            destroy_ComplexNumber(z);
            return i;
        }
        COMPLEX_NUMBER *zSquared = mul_complex(z, z);
        COMPLEX_NUMBER *zSquaredPlusC = add_complex(zSquared, z0);
        destroy_ComplexNumber(zSquared);
        destroy_ComplexNumber(z);
        z = zSquaredPlusC;
    }
    destroy_ComplexNumber(z);
    return max;
}

int main(int argc, char **argv) {
    int size = 800;
    int max = 1000;
    FILE *file = fopen(argv[1], "w");
    fprintf(file, "%d,%d\n", size, max);
    int total = size * size;
    int current = 0;
    for(int x = 0; x < size; x++) {
        for(int y = 0; y < size; y++) {
            double z0x = map(x, 0, size, -2, 2);
            double z0y = map(y, 0, size, -2, 2);
            COMPLEX_NUMBER *z0 = new_ComplexNumber(z0x, z0y);
            int man = mand(z0, max);
            destroy_ComplexNumber(z0);
            fprintf(file, "%d,%d,%d\n", x, y, man);
            double percentage = (current++) / (double) total;
            percentage *= 100;
            printf("%.2lf%%\n", percentage);
        }
    }
    fclose(file);
    return 0;
}
