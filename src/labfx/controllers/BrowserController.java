package labfx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import labfx.controllers.page.Page;

/**
 * Date: 06.12.13
 * Time: 13:02
 */
public class BrowserController extends Page {
    @FXML
    private TextField addressField;
    @FXML
    private WebView browser;

    @Override
    protected void onLoaded() {
        addressField.requestFocus();
        addressField.setText("http://google.com.ua/");
        webGo();
    }

    public void webGo() {
        browser.getEngine().load(addressField.getText());
    }
}
