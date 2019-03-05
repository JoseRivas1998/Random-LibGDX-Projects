package com.tcg.imagefun;

public final class Util {

    public static <T> void swap(T[] arr, int a, int b) {
        T temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    public static <T extends Comparable<T>> int getMin(T[] arr, int start) {
        int minIndex = start;
        T minValue = arr[start];
        for (int j = minIndex + 1; j < arr.length; j++) {
            if(minValue.compareTo(arr[j]) > 0) {
                minIndex = j;
                minValue = arr[j];
            }
        }
        return minIndex;
    }

    public static <T extends Comparable<T>> int getMax(T[] arr, int start) {
        int maxIndex = start;
        T maxValue = arr[start];
        for (int j = maxIndex + 1; j < arr.length; j++) {
            if(maxValue.compareTo(arr[j]) < 0) {
                maxIndex = j;
                maxValue = arr[j];
            }
        }
        return maxIndex;
    }

}
