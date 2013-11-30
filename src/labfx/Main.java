package labfx;

import javafx.application.Application;
import javafx.stage.Stage;
import labfx.controllers.LoginController;
import labfx.controllers.MainController;
import labfx.controllers.page.Action;
import labfx.controllers.page.EventArgs;
import labfx.controllers.page.Page;
import labfx.models.User;
import labfx.views.View;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception {
        User user = new User();
        user.setAdmin(true);
        Navigator.loadView(primaryStage, View.MAIN, 1066, 600, "JavaFX").setParameter(user);

        /*LoginController login = Navigator.loadView(primaryStage, View.LOGIN, 360, 195, "Login");
        login.setReturnAction(new Action<User>() {
            @Override
            public void action(EventArgs<User> e) {
                try
                {
                    MainController main = Navigator.loadView(primaryStage, View.MAIN, 1066, 600, "JavaFX");
                    main.setParameter(e.getEventData());
                }
                catch (IOException ex) {  }
            }
        });*/
    }

    public static void main(String[] args) {
        launch(args);
    }
}
