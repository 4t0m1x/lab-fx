package labfx.views;

/**
 * Date: 29.11.13
 * Time: 13:48
 */
public enum View {
    LOGIN("views/login.fxml"),
    MAIN("views/main.fxml"),
    PLAYER("views/player.fxml"),
    USERS("views/users.fxml"),
    BROWSER("views/browser.fxml"),
    CHART("views/chart.fxml")
    ;

    private View(String path) {
        this.path = path;
    }

    private String path;

    @Override
    public String toString() {
        return path;
    }
}
