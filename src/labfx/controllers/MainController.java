package labfx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import labfx.Navigator;
import labfx.controllers.page.Page;
import labfx.models.User;
import labfx.views.View;

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
            showMediaPlayer();
        }
    }

    private void loadView(View view)
    {
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
}
