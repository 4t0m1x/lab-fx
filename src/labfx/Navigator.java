package labfx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import labfx.controllers.page.Page;
import labfx.controllers.page.PageFunction;
import labfx.views.View;

import java.io.IOException;

/**
 * User: Vladislav
 * Date: 11.11.13
 * Time: 20:00
 */
public final class Navigator {

    public static <T extends Page> T loadInto(Pane root, View view) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(view.toString()));
        try
        {
            Parent content = (Parent)loader.load();

            root.getChildren().clear();
            root.getChildren().add(content);

        } catch (IOException e) {
            return null;
        }

        Object controller = loader.getController();
        if (controller instanceof Page) {
            Page page = (Page)controller;
            page.onLoaded();
            return (T)page;
        }

        return null;
    }

    public static <T extends Page> T loadView(Stage host, View view, double width,
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

    public static <T extends Page> T loadView(Stage host, View view, double width, double height)
            throws IOException {
        return loadView(host, view, width, height, "Untitled");
    }

    public static <T extends Page> T loadView(Stage host, View view) throws IOException {
        return loadView(host, view, 640, 480);
    }
}
