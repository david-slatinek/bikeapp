package com.david.data;

import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) {
        Cyclist cyclist = new Cyclist("David", "Slatinek");

        cyclist.setTours(Cyclist.getArrayListOfRandomTours(10));
        cyclist.sortTours();

        try {
            for (int i = 0; i < cyclist.size(); i++) {
                System.out.println(cyclist.getTours().get(i) + "\n");
            }
        } catch (NoToursException exception) {
            exception.printStackTrace();
        }


        Cyclist dummy = new Cyclist("John", "Doe");

        try {
            System.out.println(dummy.getMaxTour().toString());
        } catch (NoSuchElementException exception) {
            exception.printStackTrace();
        }

        try {
            int[] array = new int[5];
            System.out.println(array[10]);
        } catch (IndexOutOfBoundsException exception) {
            exception.printStackTrace();
        }

        try {
            System.out.println(4 / 0);
        } catch (ArithmeticException exception) {
            exception.printStackTrace();
        }

        try {
            System.out.println(dummy.getMinTour().toString());
        } catch (NoToursException exception) {
            exception.printStackTrace();
        }

    }
}
