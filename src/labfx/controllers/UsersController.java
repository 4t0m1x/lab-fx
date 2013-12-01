package labfx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import labfx.controllers.page.Page;
import labfx.data.GenericDAO;
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

    private GenericDAO<User> userDAO;

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

        userDAO = new GenericDAO<>(User.class);
        usersTable.setItems(userDAO.getAll());
    }

    @FXML
    private void addUser(ActionEvent actionEvent) {
        clearError();

        User newUser = new User();

        newUser.setUserName(newUsername.getText());
        newUser.setPassword(newPassword.getText());
        newUser.setPhone(newPhone.getText());
        newUser.setCountry(newCountry.getText());
        newUser.setAdmin(newAdmin.isSelected());
        newUser.setBanned(newBanned.isSelected());

        if (newUser.getUserName().isEmpty() ||
            newUser.getPassword().isEmpty()) {
            error("Enter username and password, please");
            return;
        }

        for (User user : usersTable.getItems()) {
            if (user.getUserName().equals(newUser.getUserName())) {
                error(String.format("A user with name \"%s\" already exists", user.getUserName()));
                return;
            }
        }

        usersTable.getItems().add(newUser);
        userDAO.create(newUser);

        newUsername.clear();
        newPassword.clear();
        newPhone.clear();
        newCountry.clear();
        newAdmin.setSelected(false);
        newBanned.setSelected(false);
    }

    @FXML
    private void deleteUser(ActionEvent actionEvent) {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            usersTable.getItems().remove(selectedUser);
            userDAO.delete(selectedUser);
            usersTable.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void saveUsers(ActionEvent actionEvent) {
        clearError();

        boolean foundNotBannedAdmin = false;
        for (User user : usersTable.getItems()) {
            if (user.getAdmin() && !user.getBanned()) {
                foundNotBannedAdmin = true;
                return;
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

    @Override
    protected void onClosed() {
        if (userDAO != null) {
            try {
                userDAO.close();
            } catch (Exception e){
            }
        }
    }
}
