package alex.moneymanager.entities.db;

import alex.moneymanager.entities.enums.Type;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Organization extends RealmObject {

    @PrimaryKey
    private int id;

    private String name;

    private String description;

    @Type
    private String type;

    private boolean publicAccess;

    private User creator;

    private RealmList<User> members;

    private RealmList<Account> accounts;

    private boolean enabled;

    private String createdAt;

    private String updatedAt;

    public Organization() {
    }

    public Organization(int id, String name, String description, String type, boolean publicAccess,
                        User creator, RealmList<User> members, RealmList<Account> accounts,
                        boolean enabled, String createdAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.publicAccess = publicAccess;
        this.creator = creator;
        this.members = members;
        this.accounts = accounts;
        this.enabled = enabled;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isPublicAccess() {
        return publicAccess;
    }

    public void setPublicAccess(boolean publicAccess) {
        this.publicAccess = publicAccess;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public RealmList<User> getMembers() {
        return members;
    }

    public void setMembers(RealmList<User> members) {
        this.members = members;
    }

    public RealmList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(RealmList<Account> accounts) {
        this.accounts = accounts;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", publicAccess=" + publicAccess +
                ", creator=" + creator +
                ", members=" + members +
                ", accounts=" + accounts +
                ", enabled=" + enabled +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}