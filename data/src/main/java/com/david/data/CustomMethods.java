package com.david.data;

public class CustomMethods {
    private static final String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

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
        for (int i = 0; i < array.length; i++)
            array[i] = CustomMethods.alphabet.charAt(CustomMethods.randomNumber(0, CustomMethods.alphabet.length() - 1));
        return new String(array);
    }

}
