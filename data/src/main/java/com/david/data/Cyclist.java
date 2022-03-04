package com.david.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Custom class Cyclist.
 */
public class Cyclist implements Sizable, Serializable {
    private final String id;
    private String name;
    private String surname;
    private final ArrayList<Tour> tours = new ArrayList<>();

    /**
     * Constructor. Sets name and surname.
     *
     * @param name    Cyclist name.
     * @param surname Cyclist surname.
     */
    public Cyclist(String name, String surname) {
        id = UUID.randomUUID().toString().replace("-", "");
        this.name = name;
        this.surname = surname;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void addTour(Tour tour) {
        tours.add(0, tour);
    }

    @Override
    public String toString() {
        return getName() + " " + getSurname();
    }

    /**
     * Gets number of tours.
     *
     * @return Number of tours.
     */
    @Override
    public int size() {
        return tours.size();
    }

    /**
     * Generates n random tours.
     *
     * @param n How many tours to generate.
     * @return ArrayList of n randomly generated tours. Null, if n < 0.
     */
    public static ArrayList<Tour> getArrayListOfRandomTours(int n) {
        if (n < 0)
            return null;

        ArrayList<Tour> list = new ArrayList<>(n);
        for (int i = 0; i < n; i++)
            list.add(Tour.getRandomTour());
        return list;
    }

     /** Gets tour at index 'position'.
     * @param position Index value.
     * @return Tour at index.
     */
    public Tour getTourAtPos(int position) {
        return tours.get(position);
    }

    /**
     * Removes tour at index 'position'.
     * @param position Index value.
     */
    public void removeAt(int position) {
        tours.remove(position);
    }
}