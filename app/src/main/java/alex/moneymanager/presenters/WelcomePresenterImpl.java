package alex.moneymanager.presenters;

import alex.moneymanager.models.CategoryModel;
import alex.moneymanager.models.CurrencyModel;
import alex.moneymanager.models.InitialDataModel;
import alex.moneymanager.utils.PreferenceUtil;
import alex.moneymanager.views.WelcomeView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WelcomePresenterImpl extends AbstractPresenter<WelcomeView>
        implements WelcomePresenter {

    private InitialDataModel initialDataModel;
    private CategoryModel categoryModel;
    private CurrencyModel currencyModel;
    private PreferenceUtil preferenceUtil;

    public WelcomePresenterImpl(InitialDataModel initialDataModel, CategoryModel categoryModel,
                                CurrencyModel currencyModel, PreferenceUtil preferenceUtil) {
        this.initialDataModel = initialDataModel;
        this.categoryModel = categoryModel;
        this.currencyModel = currencyModel;
        this.preferenceUtil = preferenceUtil;
    }

    @Override
    public void loadInitialData() {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        addSubscription(
                initialDataModel.loadInitialData()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            if (response.isSuccessful()) {
                                categoryModel.saveCategoriesDb(response.body().getCategories());
                                currencyModel.saveCurrenciesDb(response.body().getCurrencies());

                                preferenceUtil.setInitialDataLoaded(true);

                                if (isViewAttached()) {
                                    getView().dismissProgressDialog();
                                    getView().dataLoadedSuccess();
                                }
                            } else {
                                if (isViewAttached()) {
                                    getView().dismissProgressDialog();
                                    getView().showErrorDialog();
                                }
                            }
                        }, throwable -> {
                            throwable.printStackTrace();
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();
                                getView().showErrorDialog();
                            }
                        })
        );
    }
}