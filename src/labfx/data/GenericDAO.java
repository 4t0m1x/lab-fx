package labfx.data;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;

/**
 * Date: 15.10.13
 * Time: 14:25
 */
public class GenericDAO<T extends Serializable> {
    private Session session;
    private Transaction transaction;
    private Class<T> type;

    public GenericDAO(Class<T> type) {
        this.type = type;
        session = Hibernate.getSessionFactory().openSession();
        transaction = session.getTransaction();
        transaction.begin();
    }

    @Override
    protected void finalize() throws Throwable {
        transaction.commit();
        session.close();
        super.finalize();
    }

    public void create(T t) {
        session.persist(t);
    }

    public T read(long id) {
        return (T)session.get(type, id);
    }

    public void update(T t) {
        session.merge(t);
        transaction.commit();
        transaction.begin();
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

    /*public Collection<T> getAll() {
        List list = session.createQuery("SELECT t from " +
                type.getSimpleName() + " t").list();

        List<T> result = new ArrayList<T>();
        for (T t : result) {
            result.add(t);
        }

        return result;
    }*/
}
