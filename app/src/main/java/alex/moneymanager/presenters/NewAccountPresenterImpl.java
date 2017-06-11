package alex.moneymanager.presenters;

import java.util.List;

import alex.moneymanager.activities.NewAccountActivity;
import alex.moneymanager.activities.NewOperationActivity;
import alex.moneymanager.entities.db.Currency;
import alex.moneymanager.entities.network.NetworkAccount;
import alex.moneymanager.fragments.OrganizationsFragment;
import alex.moneymanager.models.AccountModel;
import alex.moneymanager.models.CurrencyModel;
import alex.moneymanager.utils.SystemUtils;
import alex.moneymanager.views.NewAccountView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewAccountPresenterImpl extends AbstractPresenter<NewAccountView>
        implements NewAccountPresenter {

    private SystemUtils systemUtils;
    private CurrencyModel currencyModel;
    private AccountModel accountModel;

    public NewAccountPresenterImpl(SystemUtils systemUtils, CurrencyModel currencyModel,
                                   AccountModel accountModel) {
        this.systemUtils = systemUtils;
        this.currencyModel = currencyModel;
        this.accountModel = accountModel;
    }

    @Override
    public void loadCurrencies() {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        if (systemUtils.isConnected()) {
            addSubscription(
                    currencyModel.currenciesApi()
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(response -> {
                                if (response.isSuccessful()) {
                                    List<Currency> currencies = response.body().getCurrencies();
                                    currencyModel.saveCurrenciesDb(currencies);

                                    if (isViewAttached()) {
                                        getView().dismissProgressDialog();
                                        getView().setCurrencies(currencies);
                                    }
                                } else {
                                    if (isViewAttached()) {
                                        getView().dismissProgressDialog();
                                        getView().showErrorDialog(
                                                NewAccountActivity.ERROR_CASE_CURRENCIES
                                        );
                                    }
                                }
                            }, throwable -> {
                                throwable.printStackTrace();
                                if (isViewAttached()) {
                                    getView().dismissProgressDialog();
                                    getView().showErrorDialog(
                                            NewAccountActivity.ERROR_CASE_CURRENCIES
                                    );
                                }
                            })
            );
        } else {
            addSubscription(
                    currencyModel.currenciesDb()
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(currencies -> {
                                if (isViewAttached()) {
                                    getView().dismissProgressDialog();
                                    getView().setCurrencies(currencies);
                                }
                            }, throwable -> {
                                throwable.printStackTrace();
                                if (isViewAttached()) {
                                    getView().dismissProgressDialog();
                                    getView().showErrorDialog(
                                            NewAccountActivity.ERROR_CASE_CURRENCIES
                                    );
                                }
                            })
            );
        }
    }

    @Override
    public void addNewUserAccount(NetworkAccount account) {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        addSubscription(
                accountModel.newUserAccountApi(account)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            if (response.isSuccessful()) {
                                accountModel.saveAccountDb(response.body().getAccount());

                                if (isViewAttached()) {
                                    getView().dismissProgressDialog();
                                    getView().accountAddedSuccess();
                                }
                            } else {
                                if (isViewAttached()) {
                                    getView().dismissProgressDialog();
                                    getView().showErrorDialog(
                                            NewAccountActivity.ERROR_CASE_NEW_USER_ACCOUNT
                                    );
                                }
                            }
                        }, throwable -> {
                            throwable.printStackTrace();
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();
                                getView().showErrorDialog(
                                        NewAccountActivity.ERROR_CASE_NEW_USER_ACCOUNT
                                );
                            }
                        })
        );
    }

    @Override
    public void loadAccountDb(int accountId) {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        addSubscription(
                accountModel.accountDb(accountId)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(account -> {
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();
                                getView().setAccount(account);
                            }
                        }, throwable -> {
                            throwable.printStackTrace();
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();
                                getView().showErrorDialog(
                                        NewAccountActivity.ERROR_CASE_LOAD_ACCOUNT
                                );
                            }
                        })
        );
    }

    @Override
    public void addNewOrganizationAccount(int organizationId, NetworkAccount account) {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        addSubscription(
                accountModel.newOrganizationAccountApi(organizationId, account)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            if (response.isSuccessful()) {
                                accountModel.saveAccountDb(response.body().getAccount());

                                if (isViewAttached()) {
                                    getView().dismissProgressDialog();
                                    getView().accountAddedSuccess();
                                }
                            } else {
                                if (isViewAttached()) {
                                    getView().dismissProgressDialog();
                                    getView().showErrorDialog(
                                            NewAccountActivity.ERROR_CASE_NEW_ORGANIZATION_ACCOUNT
                                    );
                                }
                            }
                        }, throwable -> {
                            throwable.printStackTrace();
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();
                                getView().showErrorDialog(
                                        NewAccountActivity.ERROR_CASE_NEW_ORGANIZATION_ACCOUNT
                                );
                            }
                        })
        );
    }

    @Override
    public void editOrganizationAccount(int accountId, int operationId, NetworkAccount account) {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        addSubscription(
                accountModel.editOrganizationAccountApi(accountId, operationId, account)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            if (isViewAttached()) {
                                if (response.isSuccessful()) {
                                    getView().accountAddedSuccess();
                                } else {
                                    getView().dismissProgressDialog();
                                    getView().showErrorDialog(
                                            OrganizationsFragment.ERROR_CASE_EDIT_ACCOUNT
                                    );
                                }
                            }
                        }, throwable -> {
                            throwable.printStackTrace();
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();
                                getView().showErrorDialog(
                                        OrganizationsFragment.ERROR_CASE_EDIT_ACCOUNT
                                );
                            }
                        })
        );
    }
}