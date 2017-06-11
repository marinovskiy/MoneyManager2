package alex.moneymanager.entities.network;

import alex.moneymanager.entities.enums.Type;

public class NetworkOrganization {

    private Integer id;

    private String name;

    private String description;

    @Type
    private String type;

    private boolean publicAccess;

    public NetworkOrganization() {
    }

    public NetworkOrganization(String name, String description, String type, boolean publicAccess) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.publicAccess = publicAccess;
    }

    public NetworkOrganization(int id, String name, String description, String type,
                               boolean publicAccess) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.publicAccess = publicAccess;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NetworkOrganization that = (NetworkOrganization) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "NetworkOrganization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", publicAccess=" + publicAccess +
                '}';
    }
}