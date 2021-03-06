package alex.moneymanager.entities.db;

import alex.moneymanager.entities.enums.Gender;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {

    @PrimaryKey
    private Integer id;

    private String email;

    private String firstName;

    private String lastName;

    private String password;

    private PlainPassword plainPassword;

    private String apiKey;

    private Boolean enabled;

    @Gender
    private String gender;

    private RealmList<Account> accounts;

    private RealmList<Organization> createdOrganizations;

    private RealmList<Organization> organizations;

    public User() {
    }

    // login constructor
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // registration constructor
    public User(String email, String firstName, String lastName, PlainPassword plainPassword,
                String gender) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.plainPassword = plainPassword;
        this.gender = gender;
    }

    public User(Integer id, String email, String firstName, String lastName, String password,
                PlainPassword plainPassword, String apiKey, Boolean enabled, String gender,
                RealmList<Account> accounts, RealmList<Organization> createdOrganizations,
                RealmList<Organization> organizations) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.plainPassword = plainPassword;
        this.apiKey = apiKey;
        this.enabled = enabled;
        this.gender = gender;
        this.accounts = accounts;
        this.createdOrganizations = createdOrganizations;
        this.organizations = organizations;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PlainPassword getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(PlainPassword plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public RealmList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(RealmList<Account> accounts) {
        this.accounts = accounts;
    }

    public RealmList<Organization> getCreatedOrganizations() {
        return createdOrganizations;
    }

    public void setCreatedOrganizations(RealmList<Organization> createdOrganizations) {
        this.createdOrganizations = createdOrganizations;
    }

    public RealmList<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(RealmList<Organization> organizations) {
        this.organizations = organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", plainPassword=" + plainPassword +
                ", apiKey='" + apiKey + '\'' +
                ", enabled=" + enabled +
                ", gender='" + gender + '\'' +
                ", accounts=" + accounts +
                ", createdOrganizations=" + createdOrganizations +
                ", organizations=" + organizations +
                '}';
    }
}