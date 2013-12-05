package labfx.data;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav
 * Date: 02.12.13
 * Time: 15:29
 */
public interface HibernateCommand {
    void execute(Session session);
}
