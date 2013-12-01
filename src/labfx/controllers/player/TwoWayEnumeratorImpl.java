package labfx.controllers.player;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class TwoWayEnumeratorImpl<T> implements TwoWayEnumerator<T> {
    private Collection<T> collection;
    private Iterator<T> iterator;
    private ArrayList<T> buffer;
    private int index;

    public TwoWayEnumeratorImpl(Collection<T> collection) {
        if (collection == null) throw new IllegalArgumentException();
        this.collection = collection;
        iterator = collection.iterator();
        buffer = new ArrayList<>();
        index = -1;
    }

    @Override
    public boolean movePrevious() {
        if (index <= 0) {
            return false;
        }

        --index;
        return true;
    }

    @Override
    public boolean moveNext() {
        if (index < buffer.size() - 1) {
            ++index;
            return true;
        }

        if (iterator.hasNext()) {
            buffer.add(iterator.next());
            ++index;
            return true;
        }

        return false;
    }

    @Override
    public T current() {
        if (index < 0 || index >= buffer.size()) throw new UnsupportedOperationException();
        return buffer.get(index);
    }

    @Override
    public void reset() {
        iterator = collection.iterator();
        buffer.clear();
        index = -1;
    }
}
