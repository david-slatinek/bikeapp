package com.david.data;

public class NoToursException extends Exception {
    private String name;
    private String surname;

    /**
     * Constructor. Sets cyclist name and surname.
     *
     * @param name    Cyclist name.
     * @param surname Cyclist surname.
     */
    public NoToursException(String name, String surname) {
        super();
        this.name = name;
        this.surname = surname;
    }

    /**
     * Overrides Exception method toString.
     *
     * @return Exception description.
     */
    @Override
    public String toString() {
        return "No tours for cyclist " + name + " " + surname + ".";
    }
}
