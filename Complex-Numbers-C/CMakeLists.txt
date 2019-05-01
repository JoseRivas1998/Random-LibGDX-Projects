cmake_minimum_required(VERSION 3.14)
project(Complex_Numbers_C C)

set(CMAKE_C_STANDARD 11)

set(SOURCE_FILES
        src/main.c src/ComplexNumber.h src/ComplexNumber.c)

include_directories(AFTER src)

add_executable(Complex_Numbers_C ${SOURCE_FILES})
