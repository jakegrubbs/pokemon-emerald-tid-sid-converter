package com.jakegrubbs.models;

import com.jakegrubbs.models.enums.Device;
import com.jakegrubbs.models.enums.IDType;
import com.jakegrubbs.models.enums.Language;

public class ConsoleMenu {
    public static final String userInputPrompt = System.lineSeparator() + "> ";

    public static final String languagePrompt = "What language is your game?";

    public static final String devicePrompt = "What device are you playing on?";

    public static final String idTypePrompt = "Are you modifying your TID or your SID?";

    public static String idPrompt(IDType idType) {
        return "What value do want your new " + idType + " to be?";
    }

    public static final Language[] languageOptions = Language.values();

    public static final Device[] deviceOptions = Device.values();

    public static final IDType[] idTypeOptions = IDType.values();
}