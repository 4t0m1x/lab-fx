package labfx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import labfx.auth.Auth;
import labfx.auth.LoginObject;
import labfx.auth.LoginStatus;
import labfx.controllers.page.PageFunction;
import labfx.models.User;

import java.util.HashMap;


public class LoginController extends PageFunction<User> {
    @FXML private TextField userName;
    @FXML private PasswordField password;
    @FXML private Label errorText;
    @FXML private GridPane gridPane;

    private static final HashMap<LoginStatus, String> STATUS_STRINGS = new HashMap<LoginStatus, String>();

    static {
        STATUS_STRINGS.put(LoginStatus.LoggedIn, "• Login successful");
        STATUS_STRINGS.put(LoginStatus.Rejected, "• Access denied");
        STATUS_STRINGS.put(LoginStatus.Banned, "• You are banned");
        STATUS_STRINGS.put(LoginStatus.Error, "• Connection error");
    }

    private void setLoggedIn(LoginObject loginObject) {
        if (STATUS_STRINGS.containsKey(loginObject.getStatus())) {
            errorText.setText(STATUS_STRINGS.get(loginObject.getStatus()));
        } else {
            errorText.setText("???");
        }
    }

    @FXML
    private void signIn(ActionEvent actionEvent) {
        if (userName.getText().isEmpty() || password.getText().isEmpty())
            return;

        LoginObject loginObject = Auth.logIn(userName.getText(), password.getText());
        if (loginObject.getStatus() == LoginStatus.LoggedIn) {
            onReturn(loginObject.getUser());
        }
        else {
            setLoggedIn(loginObject);
        }
    }
}
