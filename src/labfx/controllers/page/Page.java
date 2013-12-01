package labfx.controllers.page;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav
 * Date: 30.11.13
 * Time: 14:47
 */
public class Page {
    protected Object parameter;

    public final Object getParameter() {
        return parameter;
    }

    public final void setParameter(Object parameter) {
        this.parameter = parameter;
        onParameterChanged();
    }

    public final void notifyLoaded() {
        onLoaded();
    }

    public boolean canBeClosed() {
        return true;
    }

    public final void close() {
        onClosed();
    }

    protected void onParameterChanged() { }
    protected void onLoaded() { }
    protected void onClosed() { }
}
