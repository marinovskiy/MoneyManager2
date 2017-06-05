package alex.moneymanager.api.response;

import java.util.List;

import alex.moneymanager.entities.network.Category;

public class CategoriesResponse {

    private List<Category> categories;

    public CategoriesResponse() {
    }

    public CategoriesResponse(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}