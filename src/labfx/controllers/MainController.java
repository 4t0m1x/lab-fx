package labfx.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import labfx.Main;
import labfx.Navigator;
import labfx.controllers.extensions.SupportedImages;
import labfx.controllers.page.Page;
import labfx.models.User;
import labfx.views.View;
import sun.security.util.Debug;

/**
 * Date: 28.11.13
 * Time: 21:29
 */
public class MainController extends Page {

    @FXML
    private GridPane contentRoot;
    @FXML
    private Page contentPage;

    @FXML
    private Accordion accordion;

    @FXML
    private Text textUsername;

    @Override
    protected void onLoaded() {

        contentRoot.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                if (dragEvent.getDragboard().hasFiles() && new SupportedImages()
                        .accept(dragEvent.getDragboard().getFiles().get(0))) {
                    dragEvent.acceptTransferModes(TransferMode.LINK);
                    dragEvent.consume();
                }
            }
        });

        contentRoot.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                String style = String.format("-fx-background-image: url(\"%s\");" +
                        "-fx-background-repeat: stretch;" +
                        "-fx-background-size: contain;",
                        dragEvent.getDragboard().getFiles().get(0).toURI().toString());
                contentRoot.setStyle(style);
            }
        });

    }

    @Override
    protected void onParameterChanged() {
        super.onParameterChanged();
        Object parameter = getParameter();

        if (parameter != null && parameter instanceof User) {
            User user = (User) parameter;
            textUsername.setText(user.getUserName());

            if (user.getAdmin()) {
                addPane(accordion, "Users", new EventHandler<javafx.scene.input.MouseEvent>() {
                    @Override
                    public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                        loadView(View.USERS);
                    }
                });
            }
            addPane(accordion, "Player", new EventHandler<javafx.scene.input.MouseEvent>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                    loadView(View.PLAYER);
                }
            });
            addPane(accordion, "Browser", new EventHandler<javafx.scene.input.MouseEvent>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                    loadView(View.BROWSER);
                }
            });
            addPane(accordion, "Chart", new EventHandler<javafx.scene.input.MouseEvent>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                    loadView(View.CHART);
                }
            });

            Button logoutButton = new Button("Logout");
            logoutButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    logout();
                }
            });
            addPane(accordion, "Logout", null, logoutButton);


            loadView( user.getAdmin() ? View.USERS : View.PLAYER );
            accordion.getPanes().get(0).setExpanded(true);
        }
    }

    private void addPane(Accordion accordion, String text, EventHandler<javafx.scene.input.MouseEvent> onClicked,
                         Node content) {
        TitledPane pane = new TitledPane();
        pane.setText(text);
        if (onClicked != null) pane.setOnMouseClicked(onClicked);
        if (content != null) pane.setContent(content);
        accordion.getPanes().add(pane);
    }

    private void addPane(Accordion accordion, String text, EventHandler<javafx.scene.input.MouseEvent> onClicked) {
        addPane(accordion, text, onClicked, null);
    }

    @Override
    public void onClosed() {
        if (contentPage != null) {
            contentPage.close();
        }
    }

    @Override
    public boolean canBeClosed() {
        return contentPage == null || contentPage.canBeClosed();
    }

    private void loadView(View view)
    {
        if (contentPage != null) {
            if (!contentPage.canBeClosed()) {
                return;
            }

            contentPage.close();
        }

        contentPage = Navigator.loadInto(contentRoot, view);
    }

    @FXML
    private void logout() {
        if (contentPage != null) {
            if (!contentPage.canBeClosed()) {
                return;
            }

            contentPage.close();
        }
        Main.showLogin((Stage)contentRoot.getScene().getWindow());
    }
}
