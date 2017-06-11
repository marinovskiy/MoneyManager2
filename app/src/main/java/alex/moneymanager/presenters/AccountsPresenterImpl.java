package alex.moneymanager.presenters;

import alex.moneymanager.entities.db.Account;
import alex.moneymanager.models.AccountModel;
import alex.moneymanager.utils.SystemUtils;
import alex.moneymanager.views.AccountsView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AccountsPresenterImpl extends AbstractPresenter<AccountsView>
        implements AccountsPresenter {

    private SystemUtils systemUtils;
    private AccountModel accountModel;

    public AccountsPresenterImpl(SystemUtils systemUtils, AccountModel accountModel) {
        this.systemUtils = systemUtils;
        this.accountModel = accountModel;
    }

    @Override
    public void loadAccount(int accountId) {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        if (systemUtils.isConnected()) {
            addSubscription(
                    accountModel.userAccountApi(accountId)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(response -> {
                                if (response.isSuccessful()) {
                                    Account account = response.body().getAccount();
                                    accountModel.saveAccountDb(account);

                                    if (isViewAttached()) {
                                        getView().dismissProgressDialog();
                                        getView().setAccount(account);
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
        } else {
            addSubscription(
                    accountModel.userAccountDb(accountId)
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
                                    getView().showErrorDialog();
                                }
                            })
            );
        }
    }
}