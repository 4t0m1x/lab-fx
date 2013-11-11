package labfx;

import javafx.application.Application;
import javafx.stage.Stage;
import labfx.controllers.page.EventArgs;
import labfx.controllers.LoginController;
import labfx.controllers.page.PageReturnAction;
import labfx.models.User;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception {
        LoginController login = Navigator.loadPage(primaryStage, "views/login.fxml", 320, 200, "Login");
        login.setReturnAction(new PageReturnAction<User>() {
            @Override
            public void action(EventArgs<User> e) {
                try
                {
                    Navigator.loadPage(primaryStage, "views/main.fxml");
                }
                catch (IOException ex) {

                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
