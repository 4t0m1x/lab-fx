package labfx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import labfx.Navigator;
import labfx.controllers.page.PageFunction;
import labfx.models.User;
import labfx.views.View;

/**
 * Date: 28.11.13
 * Time: 21:29
 */
public class MainController extends PageFunction<User> {
    @FXML
    private GridPane contentRoot;
    @FXML
    private PageFunction<Object> contentController;

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
            loadPage(View.PLAYER);
        }
    }

    private void loadPage(View view)
    {
        contentController = Navigator.loadInto(contentRoot, view);
    }

    @FXML
    private void showMediaPlayer(ActionEvent actionEvent) {
        loadPage(View.PLAYER);

    }
}
