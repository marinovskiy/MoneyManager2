package alex.moneymanager.entities.network;

import java.util.List;

import alex.moneymanager.entities.enums.Type;

public class Organization {

    private int id;

    private String name;

    private String description;

    @Type
    private String type;

    private boolean publicAccess;

    private int creator;

    private List<User> members;

    private List<Account> accounts;

    public Organization() {
    }

    public Organization(int id, String name, String description, String type, boolean publicAccess,
                        int creator, List<User> members, List<Account> accounts) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.publicAccess = publicAccess;
        this.creator = creator;
        this.members = members;
        this.accounts = accounts;
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

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
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
                '}';
    }
}