package ru.kosstar.client.ui.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import ru.kosstar.client.exceptions.InvalidInputValueException;
import ru.kosstar.client.network.UserRepository;
import ru.kosstar.client.ui.App;
import ru.kosstar.client.ui.view.MainController;
import ru.kosstar.data.User;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AuthViewModel extends ViewModel {
    private final StringProperty login = new SimpleStringProperty("");
    private final StringProperty pass = new SimpleStringProperty("");
    private final User user = new User();
    private final StringProperty errorText = new SimpleStringProperty("");

    private final Task<User> signUp = new Task<User>() {
        @Override
        protected User call() throws Exception {
            return UserRepository.userSignUp(user);
        }

        @Override
        protected void succeeded() {
            User result = getValue();
            if (result != null) {
                final Map<String, Object> args = new HashMap<>(1);
                args.put("user", result);
                App.mainApp.loadScene(MainController.layout, true, args);
            } else {
                setErrorText(bundle.getString("error.signup.login"));
            }
        }
    };

    private final Task<User> signIn = new Task<User>() {
        @Override
        protected User call() throws Exception {
            return UserRepository.userSignIn(user);
        }

        @Override
        protected void succeeded() {
            User result = getValue();
            if (result != null) {
                final Map<String, Object> args = new HashMap<>(1);
                args.put("user", user);
                App.mainApp.loadScene(MainController.layout, true, args);
            } else {
                setErrorText(bundle.getString("error.signin"));
            }
        }
    };

    public AuthViewModel(ResourceBundle bundle) {
        super(bundle);
        if (saved != null) {
            AuthViewModel saved = (AuthViewModel) (this.saved);
            this.login.setValue(saved.login.getValue());
            this.pass.setValue(saved.pass.getValue());
            updateUserAndValidate();
        }
    }

    public void signUp() {
        if (!updateUserAndValidate()) return;
        tryExec(signUp);
    }

    public void signIn() {
        if (!updateUserAndValidate()) return;
        tryExec(signIn);
    }

    private boolean updateUserAndValidate() {
        try {
            user.setLogin(login.get());
            user.setPass(pass.get());
        } catch (InvalidInputValueException e) {
            setErrorText(bundle.getString("error.invalid." + e.getFieldId()));
            return false;
        }
        return true;
    }

    public StringProperty loginProperty() {
        return login;
    }

    public StringProperty passProperty() {
        return pass;
    }

    public StringProperty errorTextProperty() {
        return errorText;
    }

    private void setErrorText(String error) {
        errorText.setValue(error);
    }
}
