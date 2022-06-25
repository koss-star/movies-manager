package ru.kosstar.client.ui.view.controls;

import javafx.event.Event;
import javafx.util.Pair;
import javafx.util.StringConverter;
import ru.kosstar.client.ui.App;
import ru.kosstar.client.ui.view.ResourceLoader;

import java.util.Comparator;
import java.util.Locale;
import java.util.ResourceBundle;

public class ChoiceLangBox extends ChoiceBox<Locale> {

    public ChoiceLangBox() {
        super();
        initChoice();
    }

    private void initChoice() {
        getItems().addAll(ResourceLoader.getAllLanguages());
        getItems().sort(Comparator.comparing(Pair::getKey));
        setValue(new Pair<>(App.mainApp.getBundle().getString("language"), Locale.getDefault()));
        setOnAction(this::onLangChoose);
    }

    public void onLangChoose(Event event) {
        Locale.setDefault(getValue().getValue());
        App.mainApp.updateBundleByDefaultLocale();
    }

}
