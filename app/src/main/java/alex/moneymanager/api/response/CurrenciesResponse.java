package alex.moneymanager.api.response;

import java.util.List;

import alex.moneymanager.entities.db.Currency;

public class CurrenciesResponse {

    private List<Currency> currencies;

    public CurrenciesResponse() {
    }

    public CurrenciesResponse(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }
}