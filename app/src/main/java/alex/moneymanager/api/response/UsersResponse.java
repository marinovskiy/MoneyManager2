package alex.moneymanager.api.response;

import java.util.List;

import alex.moneymanager.entities.db.User;

public class UsersResponse {

    private List<User> users;

    public UsersResponse() {
    }

    public UsersResponse(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}