package alex.moneymanager.presenters;

import alex.moneymanager.models.CategoryModel;
import alex.moneymanager.models.CurrencyModel;
import alex.moneymanager.utils.PreferenceUtil;
import alex.moneymanager.views.WelcomeView;

public class WelcomePresenterImpl extends AbstractPresenter<WelcomeView>
        implements WelcomePresenter {

    private CategoryModel categoryModel;
    private CurrencyModel currencyModel;
    private PreferenceUtil preferenceUtil;

    public WelcomePresenterImpl(CategoryModel categoryModel, CurrencyModel currencyModel,
                                PreferenceUtil preferenceUtil) {
        this.categoryModel = categoryModel;
        this.currencyModel = currencyModel;
        this.preferenceUtil = preferenceUtil;
    }

    @Override
    public void loadInitialData() {

    }
}