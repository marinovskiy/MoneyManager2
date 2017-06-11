package alex.moneymanager.entities.network;

import alex.moneymanager.entities.enums.Type;

public class NetworkOperation {

    private Integer id;

    @Type
    private String type;

    private String description;

    private float sum;

    private int category;

    public NetworkOperation() {
    }

    public NetworkOperation(String type, String description, float sum, int category) {
        this.type = type;
        this.description = description;
        this.sum = sum;
        this.category = category;
    }

    public NetworkOperation(Integer id, String type, String description, float sum, int category) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.sum = sum;
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NetworkOperation that = (NetworkOperation) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "NetworkOperation{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", sum=" + sum +
                ", category=" + category +
                '}';
    }
}