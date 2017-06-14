package alex.moneymanager.api.response;

import alex.moneymanager.entities.db.News;

public class NewsResponse {

    private News news;

    public NewsResponse() {
    }

    public NewsResponse(News news) {
        this.news = news;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }
}