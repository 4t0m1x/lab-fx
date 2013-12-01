package labfx.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;

/**
 * Date: 15.10.13
 * Time: 14:25
 */
public class GenericDAO<T extends Serializable> implements AutoCloseable {
    private Session session;
    private Transaction transaction;
    private Class<T> type;

    public GenericDAO(Class<T> type) {
        this.type = type;
        session = Hibernate.getSessionFactory().openSession();
        transaction = session.getTransaction();
        transaction.begin();
    }

    public void create(T t) {
        session.persist(t);
    }

    public T read(long id) {
        return (T)session.get(type, id);
    }

    public void update(T t) {
        session.merge(t);
    }

    public void delete(T t) {
        t = (T)session.merge(t);
        session.delete(t);
    }

    public T getByField(String fieldName, String value) {
        try {
            return (T)session.createCriteria(type)
                    .add(Restrictions.eq(fieldName, value))
                    .uniqueResult();
        } catch(HibernateException ex) {
            return null;
        }
    }

    public ObservableList<T> getAll() {
        try {
            return FXCollections.observableArrayList(session.createCriteria(type).list());
        } catch (HibernateException ex) {
            return FXCollections.observableArrayList();
        }
    }

    public void commit() {
        if (transaction != null) {
            transaction.commit();
            transaction.begin();
        }
    }

    private void clean() {
        if (session.isConnected()) {
            session.close();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        clean();
        super.finalize();
    }

    @Override
    public void close() throws Exception {
        clean();
    }
}
