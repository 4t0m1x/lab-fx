package labfx.controllers.player;

import java.util.Collection;

public class EnumeratorFactory {
    public static <T> TwoWayEnumerator<T> getTwoWayEnumerator(Collection<T> collection) {
        return new TwoWayEnumeratorImpl<>(collection);
    }
}
