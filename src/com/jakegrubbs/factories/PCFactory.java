package com.jakegrubbs.factories;

import com.jakegrubbs.helpers.HexHelpers;
import com.jakegrubbs.models.Box;
import com.jakegrubbs.models.PC;

import java.util.ArrayList;
import java.util.List;

public class PCFactory {
    private final List<Box> boxes;
    private final int MAX_BOX_NAME_LENGTH = 8;

    public PCFactory() {
        boxes = new ArrayList<>();
    }

    public PC create(String hexString, String delimiter) throws PCFactoryException {
        String[] hexValues = hexString.split(delimiter);

        if (hexValues.length % MAX_BOX_NAME_LENGTH != 0) {
            throw new PCFactoryException("Number of hex values not divisible by " + MAX_BOX_NAME_LENGTH + ".");
        }

        StringBuilder pcName = new StringBuilder();

        for (String hexValue : hexValues) {
            String character = HexHelpers.characterEncodingMap.get(hexValue);
            pcName.append(character);
        }

        for (int i=0; i < pcName.length(); i += MAX_BOX_NAME_LENGTH) {
            String boxName = pcName.substring(i, Math.min(pcName.length(), i + MAX_BOX_NAME_LENGTH));
            Box box = new Box(i+1, boxName);
            boxes.add(box);
        }

        return new PC(boxes);
    }
}

