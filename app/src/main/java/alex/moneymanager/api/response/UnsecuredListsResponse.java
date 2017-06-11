package alex.moneymanager.api.response;

import java.util.List;

import alex.moneymanager.entities.db.Category;
import alex.moneymanager.entities.db.Currency;

public class UnsecuredListsResponse {

    private List<Category> categories;

    private List<Currency> currencies;

    public UnsecuredListsResponse() {
    }

    public UnsecuredListsResponse(List<Category> categories, List<Currency> currencies) {
        this.categories = categories;
        this.currencies = currencies;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }
}