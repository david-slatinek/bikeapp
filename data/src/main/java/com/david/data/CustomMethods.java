package com.david.data;

public class CustomMethods {
    /**
     * Generates random int number.
     *
     * @param min Minimum number. Included.
     * @param max Maximum number. Included.
     * @return Random int number between [min, max].
     */
    public static int randomNumber(int min, int max) {
        return (int) (Math.random() * (max - min++) + min);
    }

    /**
     * Generates random double number.
     *
     * @param min Minimum number. Included.
     * @param max Maximum number. Included.
     * @return Random double number between [min, max].
     */
    public static double randomNumber(double min, double max) {
        return Math.round((Math.random() * (max - min++) + min) * 100.0) / 100.0;
    }

    /**
     * Generates random string of param length.
     *
     * @param length String length.
     * @return Randomly generated string. Numbers, lower and upper case.
     */
    public static String getRandomString(int length) {
        char[] array = new char[CustomMethods.randomNumber(0, length)];
        final String alfabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        for (int i = 0; i < array.length; i++) {
            array[i] = alfabet.charAt(CustomMethods.randomNumber(0, alfabet.length() - 1));
        }
        return new String(array);
    }

}
