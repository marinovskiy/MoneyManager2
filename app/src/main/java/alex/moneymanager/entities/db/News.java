package alex.moneymanager.entities.db;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class News extends RealmObject {

    @PrimaryKey
    private int id;

    private String title;

    private String text;

    private String createdAt;

    private String updatedAt;

    private Integer author;

    private RealmList<Comment> comments;

    public News() {
    }

    public News(int id, String title, String text, String createdAt, String updatedAt,
                Integer author, RealmList<Comment> comments) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.author = author;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public RealmList<Comment> getComments() {
        return comments;
    }

    public void setComments(RealmList<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        News news = (News) o;

        return id == news.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", author=" + author +
                ", comments=" + comments +
                '}';
    }
}