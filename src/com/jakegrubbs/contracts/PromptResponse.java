package com.jakegrubbs.contracts;

import com.jakegrubbs.models.enums.Device;
import com.jakegrubbs.models.enums.IDType;
import com.jakegrubbs.models.enums.Language;

public class PromptResponse {
    private final Language language;
    private final Device device;
    private final IDType idType;
    private final int id;

    public PromptResponse(
            Language language,
            Device device,
            IDType idType,
            int id
    ) {
        this.language = language;
        this.device = device;
        this.idType = idType;
        this.id = id;
    }

    public Language getLanguage() {
        return language;
    }

    public Device getDevice() {
        return device;
    }

    public IDType getIdType() {
        return idType;
    }

    public int getId() {
        return id;
    }
}
