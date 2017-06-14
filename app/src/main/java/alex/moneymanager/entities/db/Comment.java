package alex.moneymanager.entities.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Comment extends RealmObject {

    @PrimaryKey
    private Integer id;

    private String text;

    private String createdAt;

    private User author;

    public Comment() {
    }

    public Comment(String text) {
        this.text = text;
    }

    public Comment(Integer id, String text, String createdAt, User author) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        return id.equals(comment.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", author=" + author +
                '}';
    }
}