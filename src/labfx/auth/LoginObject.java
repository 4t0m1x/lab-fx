package labfx.auth;

import labfx.models.User;

/**
 * User: Vladislav
 * Date: 11.11.13
 * Time: 17:02
 */
public class LoginObject {
    private User user;
    private LoginStatus status;

    public LoginObject(User user, LoginStatus status) {
        this.user = user;
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public LoginStatus getStatus() {
        return status;
    }
}
