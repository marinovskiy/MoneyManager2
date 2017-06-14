package alex.moneymanager.api.response;

import java.util.List;

import alex.moneymanager.entities.db.News;

public class NewsListResponse {

    private List<News> newsList;

    public NewsListResponse() {
    }

    public NewsListResponse(List<News> newsList) {
        this.newsList = newsList;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }
}