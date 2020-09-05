package com.jakegrubbs;

import com.jakegrubbs.models.ConsoleMenu;
import com.jakegrubbs.models.enums.Device;
import com.jakegrubbs.models.enums.IDType;
import com.jakegrubbs.models.enums.Language;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Check user's language
        System.out.println(ConsoleMenu.languagePrompt);
        for (int i = 0; i < ConsoleMenu.languageOptions.length; i++) {
            System.out.println(i+1 + ". " + ConsoleMenu.languageOptions[i]);
            if (i == ConsoleMenu.languageOptions.length - 1) {
                System.out.print(ConsoleMenu.userInputPrompt);
            }
        }

        int languageInput = getUserInput(scanner, ConsoleMenu.languageOptions.length);
        Language language = ConsoleMenu.languageOptions[languageInput - 1];
        System.out.println("Language set to '" + language + "'.\n");

        // Check user's device
        System.out.println(ConsoleMenu.devicePrompt);
        for (int i = 0; i < ConsoleMenu.deviceOptions.length; i++) {
            System.out.println(i+1 + ". " + ConsoleMenu.deviceOptions[i]);
            if (i == ConsoleMenu.deviceOptions.length - 1) {
                System.out.print(ConsoleMenu.userInputPrompt);
            }
        }

        int deviceInput = getUserInput(scanner, ConsoleMenu.deviceOptions.length);
        Device device = ConsoleMenu.deviceOptions[deviceInput - 1];
        System.out.println("Device set to '" + device + "'.\n");

        // Check user's TID/SID
        System.out.println(ConsoleMenu.idTypePrompt);
        for (int i = 0; i < ConsoleMenu.idTypeOptions.length; i++) {
            System.out.println(i+1 + ". " + ConsoleMenu.idTypeOptions[i]);
            if (i == ConsoleMenu.idTypeOptions.length - 1) {
                System.out.print(ConsoleMenu.userInputPrompt);
            }
        }

        int idTypeInput = getUserInput(scanner, ConsoleMenu.idTypeOptions.length);
        IDType idType = ConsoleMenu.idTypeOptions[idTypeInput - 1];
        System.out.println("ID type set to '" + idType + "'.\n");

        // Check user's desired TID/SID
        System.out.print(ConsoleMenu.idPrompt(idType));
        System.out.print(ConsoleMenu.userInputPrompt);
        int id = getUserInput(scanner, 65535);
        System.out.println(idType + " set to '" + String.format("%05d", id) + "'.");
    }

    public static int getUserInput(Scanner scanner, int max) {
        int input = 0;
        String message = "Please enter a valid number between 1 and " + max + "." + ConsoleMenu.userInputPrompt;

        while (scanner.hasNext())
        {
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                if (input < 1 || input > max) {
                    System.out.print(message);
                    scanner.next();
                }
                else {
                    break;
                }
            }
            else {
                System.out.print(message);
                scanner.next();
            }
        }

        return input;
    }
}