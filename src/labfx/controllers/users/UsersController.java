package labfx.controllers.users;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import labfx.controllers.page.Page;
import labfx.data.BufferedDAO;
import labfx.models.User;

public class UsersController extends Page {
    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, String> columnUsername;
    @FXML
    private TableColumn<User, String> columnPassword;
    @FXML
    private TableColumn<User, String> columnPhone;
    @FXML
    private TableColumn<User, String> columnCountry;
    @FXML
    private TableColumn<User, Boolean> columnAdmin;
    @FXML
    private TableColumn<User, Boolean> columnBanned;

    @FXML
    private TextField newUsername;
    @FXML
    private TextField newPassword;
    @FXML
    private TextField newPhone;
    @FXML
    private TextField newCountry;
    @FXML
    private CheckBox newAdmin;
    @FXML
    private CheckBox newBanned;

    @FXML
    private GridPane newAdminPane;

    @FXML
    private GridPane newBannedPane;

    private BufferedDAO<User> userDAO;

    @FXML
    private Button btnClearError;
    private boolean canBeClosed = true;

    @FXML
    private Text errorLabel;

    @Override
    protected void onLoaded() {
        columnUsername.setCellFactory(TextFieldTableCell.<User>forTableColumn());
        columnPassword.setCellFactory(TextFieldTableCell.<User>forTableColumn());
        columnPhone.setCellFactory(TextFieldTableCell.<User>forTableColumn());
        columnCountry.setCellFactory(TextFieldTableCell.<User>forTableColumn());
        columnAdmin.setCellFactory(CheckBoxTableCell.forTableColumn(columnAdmin));
        columnBanned.setCellFactory(CheckBoxTableCell.forTableColumn(columnBanned));

        columnUsername.setCellValueFactory(new PropertyValueFactory<User, String>("userName"));
        columnPassword.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<User, String>("phone"));
        columnCountry.setCellValueFactory(new PropertyValueFactory<User, String>("country"));
        columnAdmin.setCellValueFactory(new PropertyValueFactory<User, Boolean>("admin"));
        columnBanned.setCellValueFactory(new PropertyValueFactory<User, Boolean>("banned"));

        usersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        columnUsername.widthProperty().addListener(new WidthListener(newUsername));
        columnPassword.widthProperty().addListener(new WidthListener(newPassword));
        columnPhone.widthProperty().addListener(new WidthListener(newPhone));
        columnCountry.widthProperty().addListener(new WidthListener(newCountry));
        columnAdmin.widthProperty().addListener(new WidthListener(newAdminPane));
        columnBanned.widthProperty().addListener(new WidthListener(newBannedPane));

        userDAO = new BufferedDAO<>(User.class);
        usersTable.setItems(userDAO.getEntries());

        EventHandler<ActionEvent> addUser = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                addUser();
            }
        };
        newUsername.setOnAction(addUser);
        newPassword.setOnAction(addUser);
        newPhone.setOnAction(addUser);
        newCountry.setOnAction(addUser);

        usersTable.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.DELETE) {
                    deleteUser();
                }
            }
        });
    }

    private static Integer tryParseInteger(String text) {
        try {
            return new Integer(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private boolean checkUser(User user) {
        if (user.getUserName().isEmpty() ||
                user.getPassword().isEmpty()) {
            error("Enter username and password, please");
            return false;
        }

        for (User u : usersTable.getItems()) {
            if (user != u && u.getUserName().equals(user.getUserName())) {
                error(String.format("A user with name \"%s\" already exists", user.getUserName()));
                return false;
            }
        }

        if (!user.getPhone().isEmpty() &&
                tryParseInteger(user.getPhone()) == null) {
            error(String.format("Phone should only consist of digits (\"%s\")", user.getPhone()));
            return false;
        }

        return true;
    }

    @FXML
    private void addUser() {
        clearError();

        User newUser = new User();

        newUser.setUserName(newUsername.getText());
        newUser.setPassword(newPassword.getText());
        newUser.setPhone(newPhone.getText());
        newUser.setCountry(newCountry.getText());
        newUser.setAdmin(newAdmin.isSelected());
        newUser.setBanned(newBanned.isSelected());

        if (!checkUser(newUser))
            return;

        userDAO.add(newUser);

        newUsername.clear();
        newPassword.clear();
        newPhone.clear();
        newCountry.clear();
        newAdmin.setSelected(false);
        newBanned.setSelected(false);
    }

    @FXML
    private void deleteUser() {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            userDAO.remove(selectedUser);
            usersTable.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void saveUsers() {
        clearError();

        for (User user : usersTable.getItems()) {
            if (!checkUser(user))
                return;
        }

        boolean foundNotBannedAdmin = false;
        for (User user : usersTable.getItems()) {
            if (user.getAdmin() && !user.getBanned()) {
                foundNotBannedAdmin = true;
                break;
            }
        }

        if (!foundNotBannedAdmin) {
            error("At least one not banned admin should exist");
            return;
        }

        userDAO.commit();
    }

    private void error(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        btnClearError.setVisible(true);
        canBeClosed = false;
    }

    @FXML
    private void clearError() {
        errorLabel.setVisible(false);
        btnClearError.setVisible(false);
        canBeClosed = true;
    }

    @Override
    public boolean canBeClosed() {
        return canBeClosed;
    }
}
