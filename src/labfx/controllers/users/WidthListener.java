package labfx.controllers.users;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;
import javafx.scene.layout.Pane;

public class WidthListener implements ChangeListener<Number> {
    private Control control;
    private Pane pane;

    public WidthListener(Control control) {
        this.control = control;
    }

    public WidthListener(Pane pane) {
        this.pane = pane;
    }

    @Override
    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
        if (control != null) {
            control.setPrefWidth((Double)newValue);
        }
        if (pane != null) {
            pane.setPrefWidth((Double)newValue);
        }
    }
}
