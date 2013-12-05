package labfx.auth;

import labfx.data.BufferedDAO;
import labfx.models.User;
import org.hibernate.HibernateException;

/**
 * Date: 01.11.13
 * Time: 13:22
 */
public class Auth {

    public static LoginObject logIn(String userName, String password) {
        User user;
        try
        {
            BufferedDAO<User> userDAO = new BufferedDAO<>(User.class);
            user = userDAO.getByField("userName", userName);

        } catch (HibernateException e)
        {
            return new LoginObject(null, LoginStatus.Error);
        }

        if (user == null) return new LoginObject(null, LoginStatus.Rejected);

        if (user.getPassword().equals(password)) {
            if (user.getBanned()) {
                return new LoginObject(null, LoginStatus.Banned);
            }
            return new LoginObject(user, LoginStatus.LoggedIn);
        }

        return new LoginObject(null, LoginStatus.Rejected);
    }

}
