package labfx.controllers.page;

/**
 * User: Vladislav
 * Date: 11.11.13
 * Time: 20:42
 */
public interface Action<T> {
    void action(EventArgs<T> e);
}
