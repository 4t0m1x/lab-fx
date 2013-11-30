package labfx.controllers.page;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav
 * Date: 30.11.13
 * Time: 14:47
 */
public class Page {
    protected Object parameter;

    public Object getParameter() {
        return parameter;
    }

    public void setParameter(Object parameter) {
        this.parameter = parameter;
        onParameterChanged();
    }

    protected void onParameterChanged() { }
    public void onLoaded() { }
}
