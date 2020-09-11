package com.jakegrubbs.models;

public class Box {
    private int number;
    private String name;
    private final String originalName = "Box " + number;

    public Box(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return originalName + ": (" + name + ")";
    }
}
