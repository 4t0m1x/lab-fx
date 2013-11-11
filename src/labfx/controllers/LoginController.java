package labfx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import labfx.auth.Auth;
import labfx.auth.LoginObject;
import labfx.auth.LoginStatus;
import labfx.controllers.page.PageFunction;
import labfx.models.User;


public class LoginController extends PageFunction<User> {
    @FXML private TextField userName;
    @FXML private PasswordField password;
    @FXML private Text errorText;

    private static final Color COLOR_SUCCESS = Color.GREEN;
    private static final Color COLOR_FAILURE = Color.RED;

    private void setLoggedIn(LoginObject loginObject) {
        if (loginObject.getStatus() == LoginStatus.LoggedIn) {
            errorText.setText("Login successful");
            errorText.setFill(COLOR_SUCCESS);
        } else if (loginObject.getStatus() == LoginStatus.Rejected) {
            errorText.setText("Access denied");
            errorText.setFill(COLOR_FAILURE);
        } else if (loginObject.getStatus() == LoginStatus.Banned) {
            errorText.setText("You are banned");
            errorText.setFill(COLOR_FAILURE);
        } else if (loginObject.getStatus() == LoginStatus.Error) {
            errorText.setText("Logging error");
            errorText.setFill(COLOR_FAILURE);
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
