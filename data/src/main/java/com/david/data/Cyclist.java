package com.david.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

/**
 * Custom class Cyclist.
 */
public class Cyclist implements Sizable, Serializable {
    private String id;
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

    /**
     * Returns cyclist name.
     *
     * @return Cyclist name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns cyclist surname.
     *
     * @return Cyclist surname.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Adds tour to list.
     *
     * @param tour Tour to add.
     */
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

    /**
     * Overrides Object method toString.
     *
     * @return Cyclist name and surname.
     */
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
     * Gets tours average length.
     *
     * @return Tours average length.
     * @throws NoToursException If there is no tours.
     */
    public double getAverage() throws NoToursException {
        if (tours.size() == 0) throw new NoToursException(getName(), getSurname());

        double sum = 0;
        for (int i = 0; i < tours.size(); i++) {
            sum += tours.get(i).getLength();
        }
        return sum / size();
    }

    /**
     * Gets tour median.
     *
     * @return Tour median.
     * @throws NoToursException If there is no tours.
     */
    public Tour getMedian() throws NoToursException {
        if (tours.size() == 0) throw new NoToursException(getName(), getSurname());
        return size() / 2 == 1 ? tours.get((size() + 1) / 2 + 1) : tours.get((int) ((1.0 / 2.0) * (size() / 2 + size() / 2 + 1)) - 1);
    }

    /**
     * Generates n random tours.
     *
     * @param n How many tours to generate.
     * @return ArrayList of n randomly generated tours. Null, if n < 0.
     */
    public static ArrayList<Tour> getArrayListOfRandomTours(int n) {
        if (n < 0) {
            return null;
        }

        ArrayList<Tour> list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            list.add(Tour.getRandomTour());
        }
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

    public Tour getTourAtPos(int position) {
        return tours.get(position);
    }

    public void removeAt(int position) {
        tours.remove(position);
    }
}