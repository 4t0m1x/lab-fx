package labfx.controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import labfx.Main;
import labfx.Navigator;
import labfx.controllers.page.Action;
import labfx.controllers.page.EventArgs;
import labfx.controllers.page.Page;
import labfx.models.User;
import labfx.views.View;

import java.io.IOException;

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
    private Button btnUsers;

    private User user;

    @Override
    protected void onParameterChanged() {
        super.onParameterChanged();
        Object parameter = getParameter();

        if (parameter != null && parameter instanceof User)
        {
            user = (User)parameter;
            btnUsers.setVisible(user.getAdmin());
            if (user.getAdmin()) {
                showUsers();
            } else {
                showMediaPlayer();
            }
        }
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
    private void showMediaPlayer() {
        loadView(View.PLAYER);
    }

    @FXML
    private void showUsers() {
        loadView(View.USERS);
    }

    @FXML
    private void logout(ActionEvent actionEvent) {
        if (contentPage != null) {
            if (!contentPage.canBeClosed()) {
                return;
            }

            contentPage.close();
        }
        Main.showLogin((Stage)contentRoot.getScene().getWindow());
    }
}
