package com.david.data;

import java.io.Serializable;
import java.util.UUID;

/**
 * Tour class.
 */
public class Tour implements Comparable<Tour>, Serializable {
    private String id;
    private Point startPoint;
    private Point endPoint;
    private double length;
    private String description;


    /**
     * Constructor. Sets all object variables.
     *
     * @param startPoint  Tour Start time.
     * @param endPoint    Tour end time.
     * @param length      Tour length.
     * @param description Tour description.
     */
    public Tour(Point startPoint, Point endPoint, double length, String description) {
        id = UUID.randomUUID().toString().replace("-", "");
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.length = length;
        setDescription(description);
    }

    public String getId() {
        return id;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public String getDescription() {
        return description;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public void setLength(double length) {
        this.length = length;
    }

    /**
     * Sets tour description.
     *
     * @param description Tour description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Overrides Comparable compareTo method.
     *
     * @param tour Tour to compare.
     * @return Implements Double.compare.
     */
    @Override
    public int compareTo(Tour tour) {
        return Double.compare(length, tour.length);
    }

    /**
     * Overrides Object method toString.
     *
     * @return Object attributes.
     */
    @Override
    public String toString() {
        return "Start point: " + startPoint +
                "\nEnd point: " + endPoint +
                "\nLength: " + length +
                "\nDescription: " + description;
    }

    /**
     * Generates random Tour.
     *
     * @return Randomly generated Tour.
     */
    public static Tour getRandomTour() {
        Point start, end;

        do {
            start = Point.getRandomPoint();
            end = Point.getRandomPoint();
        } while (end.compareTo(start) < 0);

        return new Tour(start, end, CustomMethods.randomNumber(1.0, 100.0), CustomMethods.getRandomString(CustomMethods.randomNumber(1, 50)));
    }

    /**
     * Gets tour length.
     *
     * @return Tour length.
     */
    public double getLength() {
        return length;
    }
}
