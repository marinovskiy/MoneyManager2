package alex.moneymanager.adapters;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import alex.moneymanager.R;
import alex.moneymanager.entities.db.Account;
import alex.moneymanager.entities.db.Comment;
import alex.moneymanager.utils.DateTimeUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private OnItemClickListener onItemClickListener;

    private List<Comment> comments;

    public CommentsAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    public void updateAccounts(List<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        holder.bind(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return comments != null ? comments.size() : 0;
    }

    class CommentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_comment_number)
        TextView tvNumber;
        @BindView(R.id.tv_comment_text)
        TextView tvText;
        @BindView(R.id.tv_comment_author)
        TextView tvAuthor;
        @BindView(R.id.tv_comment_date)
        TextView tvDate;

        CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }

        void bind(Comment comment) {
            String number;
            if (getAdapterPosition() + 1 < 10) {
                number = String.format(" %s", getAdapterPosition() + 1);
            } else {
                number = String.valueOf(getAdapterPosition() + 1);
            }
            tvNumber.setText(number);

            tvText.setText(comment.getText());

            tvAuthor.setText(
                    String.format("%s %s", comment.getAuthor().getFirstName(), comment.getAuthor().getFirstName())
            );

            tvDate.setText(DateTimeUtils.covertDateStr(comment.getCreatedAt()));
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {

        void onItemClick(View view, int position);
    }
}