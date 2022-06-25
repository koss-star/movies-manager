package ru.kosstar.client.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InvalidInputValueException extends RuntimeException {

    private final String fieldId;

    public InvalidInputValueException(String id) {
        fieldId = id;
    }

    public String getFieldId() {
        return fieldId;
    }
}
