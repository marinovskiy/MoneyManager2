package alex.moneymanager.presenters;

import alex.moneymanager.entities.db.Account;
import alex.moneymanager.entities.network.NetworkOperation;
import alex.moneymanager.fragments.AccountsFragment;
import alex.moneymanager.models.AccountModel;
import alex.moneymanager.models.OperationModel;
import alex.moneymanager.utils.SystemUtils;
import alex.moneymanager.views.AccountsView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AccountsPresenterImpl extends AbstractPresenter<AccountsView>
        implements AccountsPresenter {

    private SystemUtils systemUtils;
    private AccountModel accountModel;
    private OperationModel operationModel;

    public AccountsPresenterImpl(SystemUtils systemUtils, AccountModel accountModel,
                                 OperationModel operationModel) {
        this.systemUtils = systemUtils;
        this.accountModel = accountModel;
        this.operationModel = operationModel;
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
                                        getView().showErrorDialog(
                                                AccountsFragment.ERROR_CASE_ACCOUNT
                                        );
                                    }
                                }
                            }, throwable -> {
                                throwable.printStackTrace();
                                if (isViewAttached()) {
                                    getView().dismissProgressDialog();
                                    getView().showErrorDialog(
                                            AccountsFragment.ERROR_CASE_ACCOUNT
                                    );
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
                                    getView().showErrorDialog(
                                            AccountsFragment.ERROR_CASE_ACCOUNT
                                    );
                                }
                            })
            );
        }
    }

    @Override
    public void editOperation(int accountId, int operationId, NetworkOperation operation) {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        addSubscription(
                operationModel.editOperation(accountId, operationId, operation)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();

                                if (response.isSuccessful()) {
                                    loadAccount(accountId);
                                }
                            } else {
                                getView().showErrorDialog(
                                        AccountsFragment.ERROR_CASE_EDIT_OPERATION
                                );
                            }
                        }, throwable -> {
                            throwable.printStackTrace();
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();
                                getView().showErrorDialog(
                                        AccountsFragment.ERROR_CASE_EDIT_OPERATION
                                );
                            }
                        })
        );
    }

    @Override
    public void deleteOperation(int accountId, int operationId) {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        addSubscription(
                operationModel.deleteOperation(accountId, operationId)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();

                                if (response.isSuccessful()) {
                                    loadAccount(accountId);
                                }
                            } else {
                                getView().showErrorDialog(
                                        AccountsFragment.ERROR_CASE_EDIT_OPERATION
                                );
                            }
                        }, throwable -> {
                            throwable.printStackTrace();
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();
                                getView().showErrorDialog(
                                        AccountsFragment.ERROR_CASE_EDIT_OPERATION
                                );
                            }
                        })
        );
    }
}