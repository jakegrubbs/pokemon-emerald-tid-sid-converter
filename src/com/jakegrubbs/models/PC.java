package com.jakegrubbs.models;

import java.util.List;

public class PC {
    public PC(List<Box> boxes) {
        this.boxes = boxes;
    }

    List<Box> boxes;

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }
}