package labfx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * User: Vladislav
 * Date: 11.11.13
 * Time: 20:00
 */
public final class Navigator {

    public static <T> T loadPage(Stage host, String fxmlPath, double width,
                                double height, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlPath));
        Parent root = (Parent)loader.load();
        host.setTitle(title);
        host.setScene(new Scene(root, width, height));
        host.sizeToScene();

        if (!host.isShowing()) {
            host.show();
        }

        return loader.getController();
    }

    public static <T> T loadPage(Stage host, String fxmlPath, double width, double height) throws IOException {
        return loadPage(host, fxmlPath, width, height, "Untitled");
    }

    public static <T> T loadPage(Stage host, String fxmlPath) throws IOException {
        return loadPage(host, fxmlPath, 640, 480);
    }
}
