package alex.moneymanager.entities.network;

import java.util.List;

public class Account {

    private int id;

    private String name;

    private String description;

    private Currency currency;

    private float balance;

    private Integer user;

    private List<Operation> operations;

    private Integer organization;

    public Account() {
    }

    public Account(int id, String name, String description, Currency currency, float balance,
                   Integer user, List<Operation> operations, Integer organization) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.currency = currency;
        this.balance = balance;
        this.user = user;
        this.operations = operations;
        this.organization = organization;
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public Integer getOrganization() {
        return organization;
    }

    public void setOrganization(Integer organization) {
        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return id == account.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", currency=" + currency +
                ", balance=" + balance +
                ", user=" + user +
                ", operations=" + operations +
                ", organization=" + organization +
                '}';
    }
}