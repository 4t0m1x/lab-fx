package labfx.controllers.page;

/**
 * User: Vladislav
 * Date: 11.11.13
 * Time: 20:36
 */
public class PageFunction<T> {
    private Action<T> returnAction;
    private Object parameter;

    public void setParameter(Object parameter) {
        this.parameter = parameter;
        onParameterChanged();
    }

    protected Object getParameter() {
        return parameter;
    }

    public void setReturnAction(Action<T> value) {
        returnAction = value;
    }

    protected void onReturn(T eventData) {
        if (returnAction != null) {
            returnAction.action(new EventArgs<T>(this, eventData));
        }
    }

    protected void onParameterChanged() { }
}
