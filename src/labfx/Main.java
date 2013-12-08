package labfx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import labfx.controllers.LoginController;
import labfx.controllers.MainController;
import labfx.controllers.page.Action;
import labfx.controllers.page.EventArgs;
import labfx.models.User;
import labfx.views.View;

import java.io.IOException;

public class Main extends Application {
    static MainController main;

    private static void setMain(MainController main) {
        Main.main = main;
    }

    public static void showLogin(final Stage stage) {
        try {
            LoginController login = Navigator.loadView(stage, View.LOGIN, 360, 195, "Login");
            login.setReturnAction(new Action<User>() {
                @Override
                public void action(EventArgs<User> e) {
                    try {
                        MainController main = Navigator.loadView(stage, View.MAIN, 1066, 600, "JavaFX");
                        main.setParameter(e.getEventData());
                        setMain(main);
                    }
                    catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            Platform.exit();
        }
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        showLogin(primaryStage);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                if (main != null) {
                    if (!main.canBeClosed()) {
                        windowEvent.consume();
                    } else {
                        main.close();
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
