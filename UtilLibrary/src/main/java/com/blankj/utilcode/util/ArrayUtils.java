package com.blankj.utilcode.util;

/**
 * @author by WuXiang on 2017/12/26.
 */

public class ArrayUtils {
    public static int[] insert(int a[], int index, int value) {
        int b[] = new int[a.length + 1];
        for (int i = 0; i < b.length; i++) {
            if (i < index - 1) {
                b[i] = a[i];
            }
            if (i == index - 1) {
                b[i] = value;
            }
            if (i > index - 1) {
                b[i] = a[i - 1];
            }
        }
        return b;
    }

    public static int[] delete(int a[], int index) {
        int b[] = new int[a.length - 1];
        for (int i = 0; i < b.length; i++) {
            if (i < index - 1) {
                b[i] = a[i];
            } else {
                b[i] = a[i + 1];
            }
        }
        return b;
    }

    public static int[] update(int a[], int index, int value) {
        a[index - 1] = value;
        return a;
    }

    public static int[] add(int a[], int value) {
        int b[] = new int[a.length + 1];
        System.arraycopy(a, 0, b, 0, a.length);
        b[a.length] = value;
        return b;
    }

    public static float[] insert(float a[], int index, float value) {
        float b[] = new float[a.length + 1];
        for (int i = 0; i < b.length; i++) {
            if (i < index - 1) {
                b[i] = a[i];
            }
            if (i == index - 1) {
                b[i] = value;
            }
            if (i > index - 1) {
                b[i] = a[i - 1];
            }
        }
        return b;
    }

    public static float[] delete(float a[], int index) {
        float b[] = new float[a.length - 1];
        for (int i = 0; i < b.length; i++) {
            if (i < index - 1) {
                b[i] = a[i];
            } else {
                b[i] = a[i + 1];
            }
        }
        return b;
    }

    public static float[] update(float a[], int index, float value) {
        a[index - 1] = value;
        return a;
    }

    public static float[] add(float a[], float value) {
        float b[] = new float[a.length + 1];
        System.arraycopy(a, 0, b, 0, a.length);
        b[a.length] = value;
        return b;
    }
}