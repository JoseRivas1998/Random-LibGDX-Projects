package com.tcg.imagefun;

import java.util.Stack;

public class QuickSortByRow extends ImageEffectBase {

    private int row = -1;

    private Stack<Integer> stack;
    private boolean justStarting;


    @Override
    public void create() {
        super.create();
        stack = new Stack<>();
        justStarting = true;
    }

    @Override
    public void render() {
        super.render();
        sort();
    }

    private void sort() {
        if(row < this.image.pixels.length) {
            if(!stack.isEmpty()) {
                int h = stack.pop();
                int l = stack.pop();
                int p = partition(this.image.pixels[row], l, h);
                if(p - 1 > l) {
                    stack.push(l);
                    stack.push(p - 1);
                }
                if(p + 1 < h) {
                    stack.push(p + 1);
                    stack.push(h);
                }
            } else {
                row++;
                if(row < this.image.pixels.length) {
                    stack.push(0);
                    stack.push(this.image.pixels[row].length - 1);
                }
            }
        }
    }

    private int partition(MyImage.MyColor[] arr, int low, int high) {
        MyImage.MyColor pivot = arr[high];
        int i = low - 1;
        for (int j = low; j <= high - 1; j++) {
            if(arr[j].compareTo(pivot) <= 0) {
                i++;
                Util.swap(arr, i, j);
            }
        }
        Util.swap(arr, i + 1, high);
        return i + 1;
    }

}
