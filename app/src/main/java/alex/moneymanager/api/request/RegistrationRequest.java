package alex.moneymanager.api.request;

import alex.moneymanager.entities.db.User;

public class RegistrationRequest {

    private User user;

    public RegistrationRequest() {
    }

    public RegistrationRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}