package alex.moneymanager.api.request;

import alex.moneymanager.entities.db.Comment;

public class AddCommentRequest {

    private Comment comment;

    public AddCommentRequest() {
    }

    public AddCommentRequest(Comment comment) {
        this.comment = comment;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}