package ru.kosstar.client.ui.view.controls;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.util.Pair;
import javafx.util.StringConverter;
import ru.kosstar.client.ui.App;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

public class ChoiceBox<T> extends ComboBox<Pair<String, T>> {

    public ChoiceBox() {
        super();
        getStyleClass().add("clickable");
        setConverter(new StringConverter<Pair<String, T>>() {
            @Override
            public String toString(Pair<String, T> object) {
                return object.getKey();
            }

            @Override
            public Pair<String, T> fromString(String string) {
                return null;
            }
        });
    }

    public void setItemList(List<Pair<String, T>> items) {
        if (items.size() == 0)
            return;
        Collator collator = Collator.getInstance(Locale.getDefault());
        this.setItems(FXCollections.observableList(
                items.stream()
                        .sorted(((o1, o2) -> collator.compare(o1.getKey(), o2.getKey())))
                        .collect(Collectors.toList())
        ));
        setValue(getItems().get(0));
    }

    public T getSelected() {
        return super.getValue().getValue();
    }

    public void setSelected(T selected) {
        if (getItems().size() == 0)
            return;
        Optional<Pair<String, T>> find = getItems().stream().filter(it -> it.getValue().equals(selected)).findFirst();
        find.ifPresent(this::setValue);
    }

}


