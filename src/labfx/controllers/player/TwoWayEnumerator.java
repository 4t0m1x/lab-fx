package labfx.controllers.player;

public interface TwoWayEnumerator<T> {
    boolean movePrevious();
    boolean moveNext();
    T current();
    void reset();
}
