package labfx.auth;

import labfx.data.GenericDAO;
import labfx.models.User;

/**
 * Date: 01.11.13
 * Time: 13:22
 */
public class Auth {

    public static LoginObject logIn(String userName, String password) {
        GenericDAO<User> userDAO = new GenericDAO<User>(User.class);
        User user = userDAO.getByField("userName", userName);

        if (user == null) return new LoginObject(null, LoginStatus.Error);

        if (user.getPassword().equals(password)) {
            if (user.getBanned()) {
                return new LoginObject(null, LoginStatus.Banned);
            }
            return new LoginObject(user, LoginStatus.LoggedIn);
        }

        return new LoginObject(null, LoginStatus.Rejected);
    }

}
