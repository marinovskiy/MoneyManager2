package alex.moneymanager.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import alex.moneymanager.R;
import alex.moneymanager.entities.db.News;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private OnItemClickListener onItemClickListener;

    private List<News> newsList;

    public NewsAdapter(List<News> newsList) {
        this.newsList = newsList;
    }

    public void updateNewss(List<News> newsList) {
        this.newsList = newsList;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        holder.bindNews(newsList.get(position));
    }

    @Override
    public int getItemCount() {
        return newsList != null ? newsList.size() : 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {

        void onItemClick(int position);
    }

    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_news_title)
        TextView tvTitle;
        @BindView(R.id.tv_news_date)
        TextView tvDate;
        @BindView(R.id.tv_news_short_text)
        TextView tvShortText;
        @BindView(R.id.tv_news_comments_number)
        TextView tvCommentsNumber;

        NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(getAdapterPosition());
            }
        }

        void bindNews(News news) {
            tvTitle.setText(news.getTitle());
            tvDate.setText(news.getUpdatedAt());
            tvShortText.setText(news.getText());
            tvCommentsNumber.setText(
                    String.format(
                            "Коментарі: %s",
                            news.getComments() != null ? news.getComments().size() : 0
                    )
            );
        }
    }
}