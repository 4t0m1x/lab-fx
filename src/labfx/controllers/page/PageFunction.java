package labfx.controllers.page;

/**
 * User: Vladislav
 * Date: 11.11.13
 * Time: 20:36
 */
public class PageFunction<T> extends Page {
    private Action<T> returnAction;

    public void setReturnAction(Action<T> value) {
        returnAction = value;
    }

    protected void onReturn(T eventData) {
        if (returnAction != null) {
            returnAction.action(new EventArgs<T>(this, eventData));
        }
    }
}
