package com.jakegrubbs.helpers;

public class HexHelpers {
    public static final String[] CHAR_ARRAY = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "!", "?", ".", "-",
            "…", "”", "”", "‘", "’", "♂", "♀", ", ", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h",
            "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };

    public String hexToChar(int hex) {
        if (hex < 0xAF)
            hex -= 168;
        else if (hex < 0xB7)
            hex -= 167;
        else if (hex < 0xB9)
            hex -= 166;
        else
            hex -= 165;

        return CHAR_ARRAY[hex];
    }
}
