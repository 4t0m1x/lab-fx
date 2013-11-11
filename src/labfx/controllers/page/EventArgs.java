package labfx.controllers.page;

/**
 * User: Vladislav
 * Date: 11.11.13
 * Time: 20:42
 */
public class EventArgs<T> {
    Object source;
    T eventData;

    public EventArgs(Object source, T eventData) {
        this.source = source;
        this.eventData = eventData;
    }

    public Object getSource() {
        return source;
    }

    public T getEventData() {
        return eventData;
    }
}
