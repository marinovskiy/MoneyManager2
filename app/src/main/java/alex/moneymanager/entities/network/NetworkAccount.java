package alex.moneymanager.entities.network;

public class NetworkAccount {

    private Integer id;

    private String name;

    private String description;

    private int currency;

//    private float balance;
//
//    private Integer user;
//
//    private List<Operation> operations;
//
//    private Integer organization;
//
//    private String createdAt;
//
//    private String updatedAt;

    public NetworkAccount() {
    }

    public NetworkAccount(String name, String description, int currency) {
        this.name = name;
        this.description = description;
        this.currency = currency;
    }

//    public NetworkAccount(int id, String name, String description, Currency currency, float balance,
//                          Integer user, List<Operation> operations, Integer organization,
//                          String createdAt, String updatedAt) {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//        this.currency = currency;
//        this.balance = balance;
//        this.user = user;
//        this.operations = operations;
//        this.organization = organization;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

//    public float getBalance() {
//        return balance;
//    }
//
//    public void setBalance(float balance) {
//        this.balance = balance;
//    }
//
//    public Integer getUser() {
//        return user;
//    }
//
//    public void setUser(Integer user) {
//        this.user = user;
//    }
//
//    public List<Operation> getOperations() {
//        return operations;
//    }
//
//    public void setOperations(List<Operation> operations) {
//        this.operations = operations;
//    }
//
//    public Integer getOrganization() {
//        return organization;
//    }
//
//    public void setOrganization(Integer organization) {
//        this.organization = organization;
//    }
//
//    public String getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(String createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public String getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(String updatedAt) {
//        this.updatedAt = updatedAt;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NetworkAccount account = (NetworkAccount) o;

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
//                ", balance=" + balance +
//                ", user=" + user +
//                ", operations=" + operations +
//                ", organization=" + organization +
//                ", createdAt='" + createdAt + '\'' +
//                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}