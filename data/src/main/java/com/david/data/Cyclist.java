package com.david.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

/**
 * Custom class Cyclist.
 */
public class Cyclist implements Sizable, Serializable {
    private final String id;
    private String name;
    private String surname;
    private ArrayList<Tour> tours = new ArrayList<>();

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
        tours.add(tour);
    }

    /**
     * Sorts tours by tour length.
     */
    public void sortTours() {
        Collections.sort(tours);
    }

    /**
     * Returns tours for iteration.
     *
     * @return Tours.
     * @throws NoToursException If there is no tours.
     */
    public ArrayList<Tour> getTours() throws NoToursException {
        if (size() == 0) throw new NoToursException(getName(), getSurname());
        return tours;
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
     * Gets tour with max length.
     *
     * @return Tour with max length.
     */
    public Tour getMaxTour() {
        return Collections.max(tours);
    }

    /**
     * Gets tour with min length.
     *
     * @return Tour with min length.
     * @throws NoToursException If there is no tours.
     */
    public Tour getMinTour() throws NoToursException {
        if (tours.size() == 0) throw new NoToursException(getName(), getSurname());
        return Collections.min(tours);
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

    /**
     * Sets Tours list.
     *
     * @param tours Tours to set.
     */
    public void setTours(ArrayList<Tour> tours) {
        this.tours = tours;
    }

    /**
     * Gets tour at index 'position'.
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