package com.jakegrubbs.models;

import java.util.List;

public class PC {
    private List<Box> boxes;

    public PC(List<Box> boxes) {
        this.boxes = boxes;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }

    public String toString() {
        StringBuilder pc = new StringBuilder();

        for (Box box : boxes) {
            String boxString = box.toString() + "\n";
            pc.append(boxString);
        }

        return pc.toString();
    }
}