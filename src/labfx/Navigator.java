package labfx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import labfx.controllers.page.PageFunction;
import labfx.views.View;

import java.io.IOException;

/**
 * User: Vladislav
 * Date: 11.11.13
 * Time: 20:00
 */
public final class Navigator {

    public static <T extends PageFunction<?>> T loadInto(Pane root, View view) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(view.toString()));
        try
        {
            Parent content = (Parent)loader.load();

            root.getChildren().clear();
            root.getChildren().add(content);

        } catch (IOException e) {
            return null;
        }


        return loader.getController();
    }

    public static <T extends PageFunction<?>> T loadPage(Stage host, View view, double width,
                                double height, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(view.toString()));
        Parent root = (Parent)loader.load();
        host.setTitle(title);
        host.setScene(new Scene(root, width, height));
        host.sizeToScene();

        if (!host.isShowing()) {
            host.show();
        }

        return loader.getController();
    }

    public static <T extends PageFunction<?>> T loadPage(Stage host, View view, double width, double height)
            throws IOException {
        return loadPage(host, view, width, height, "Untitled");
    }

    public static <T extends PageFunction<?>> T loadPage(Stage host, View view) throws IOException {
        return loadPage(host, view, 640, 480);
    }
}
