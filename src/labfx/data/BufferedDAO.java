package labfx.data;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav
 * Date: 02.12.13
 * Time: 15:23
 */
public class BufferedDAO<T> {
    private Class<T> type;

    private ObservableList<T> entries = FXCollections.observableArrayList();
    private ArrayList<T> removedEntries = new ArrayList<>();

    public BufferedDAO(Class<T> type) {
        this.type = type;
    }

    public void add(T entry) {
        if (entries == null) throw new IllegalStateException();
        entries.add(entry);
    }

    public void remove(T entry) {
        if (entries == null) throw new IllegalStateException();

        if (entries.contains(entry)) {
            int entryIndex = entries.indexOf(entry);
            entries.remove(entryIndex);
            removedEntries.add(entry);
        }
    }

    public void commit() {
        if (entries == null) throw new IllegalStateException();

        for (final T entry : entries) {
            execute(new HibernateCommand() {
                @Override
                public void execute(Session session) {
                    session.saveOrUpdate(session.merge(entry));

                }
            });
        }

        execute(new HibernateCommand() {
            @Override
            public void execute(Session session) {
                for (T removedEntry : removedEntries) {
                    session.delete(session.merge(removedEntry));
                }
            }
        });

        removedEntries.clear();
    }

    public ObservableList<T> getEntries() {
        final ObjectProperty<ObservableList<T>> result = new SimpleObjectProperty<>();

        execute(new HibernateCommand() {
                @Override
                public void execute(Session session) {
                result.set(FXCollections.observableArrayList(session.createCriteria(type).list()));
            }
            });

        entries = result.get();

        return entries;
    }

    public T getByField(final String fieldName, final String value) {
        final ObjectProperty<T> entry = new SimpleObjectProperty<>();

        execute(new HibernateCommand() {
            @Override
            public void execute(Session session) {
                Object result = session.createCriteria(type)
                        .add(Restrictions.eq(fieldName, value))
                        .uniqueResult();
                entry.set((T) result);
            }
        });

        return entry.get();
    }

    private void execute(HibernateCommand command) {
        Session session = Hibernate.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            command.execute(session);
            transaction.commit();
        }
        catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
        finally {
            session.close();
        }
    }
}
