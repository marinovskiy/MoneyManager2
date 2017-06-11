package alex.moneymanager.entities.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Feedback extends RealmObject {

    @PrimaryKey
    private int id;

    private String text;

    private String createdAt;

    private User author;

    private boolean enabled;

    public Feedback() {
    }

    public Feedback(int id, String text, String createdAt, User author, boolean enabled) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.author = author;
        this.enabled = enabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Feedback feedback = (Feedback) o;

        return id == feedback.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", author=" + author +
                ", enabled=" + enabled +
                '}';
    }
}