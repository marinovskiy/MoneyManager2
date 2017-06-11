package alex.moneymanager.api.request;

import alex.moneymanager.entities.db.User;

public class UserLoginRequest {

    private User user;

    public UserLoginRequest() {
    }

    public UserLoginRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}