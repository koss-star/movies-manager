package ru.kosstar.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InvalidInputValueException extends RuntimeException {
    private final List<String> inputIds;

    public InvalidInputValueException(String... ids) {
        inputIds = new ArrayList<>();
        inputIds.addAll(Arrays.asList(ids));
    }

    public List<String> getInputIds() {
        return Collections.unmodifiableList(inputIds);
    }
}
