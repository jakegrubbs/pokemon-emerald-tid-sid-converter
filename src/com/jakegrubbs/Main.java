package com.jakegrubbs;

import com.jakegrubbs.contracts.PromptResponse;
import com.jakegrubbs.factories.PCFactory;
import com.jakegrubbs.factories.PCFactoryException;
import com.jakegrubbs.models.PC;
import com.jakegrubbs.services.PromptService;

public class Main {
    private static final String SPACE_DELIMITER = " ";

    public static void main(String[] args) throws PCFactoryException {
        PromptService promptService = new PromptService();
        PromptResponse response = promptService.prompt();

        // TODO: Get full hex string for PC boxes
        String hexString = "";

        try {
            PCFactory pcFactory = new PCFactory();
            PC pc = pcFactory.create(hexString, SPACE_DELIMITER);
            System.out.println(pc.toString());
        }
        catch (PCFactoryException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }
}