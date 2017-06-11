package alex.moneymanager.presenters;

import alex.moneymanager.activities.AccountDetailsActivity;
import alex.moneymanager.entities.db.Account;
import alex.moneymanager.entities.network.NetworkOperation;
import alex.moneymanager.models.AccountModel;
import alex.moneymanager.models.OperationModel;
import alex.moneymanager.utils.SystemUtils;
import alex.moneymanager.views.AccountDetailsView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AccountDetailsPresenterImpl extends AbstractPresenter<AccountDetailsView>
        implements AccountDetailsPresenter {

    private SystemUtils systemUtils;
    private AccountModel accountModel;
    private OperationModel operationModel;

    public AccountDetailsPresenterImpl(SystemUtils systemUtils, AccountModel accountModel,
                                       OperationModel operationModel) {
        this.systemUtils = systemUtils;
        this.accountModel = accountModel;
        this.operationModel = operationModel;
    }

    @Override
    public void loadAccount(int organizationId, int accountId) {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        if (systemUtils.isConnected()) {
            addSubscription(
                    accountModel.organizationAccountApi(organizationId, accountId)
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
                                                AccountDetailsActivity.ERROR_CASE_ACCOUNT
                                        );
                                    }
                                }
                            }, throwable -> {
                                throwable.printStackTrace();
                                if (isViewAttached()) {
                                    getView().dismissProgressDialog();
                                    getView().showErrorDialog(
                                            AccountDetailsActivity.ERROR_CASE_ACCOUNT
                                    );
                                }
                            })
            );
        } else {
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
                                            AccountDetailsActivity.ERROR_CASE_ACCOUNT
                                    );
                                }
                            })
            );
        }
    }

    @Override
    public void editOperation(int organizationId, int accountId, int operationId, NetworkOperation operation) {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        addSubscription(
                operationModel.editOrganizationOperation(organizationId, accountId, operationId, operation)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();

                                if (response.isSuccessful()) {
                                    loadAccount(organizationId, accountId);
                                }
                            } else {
                                getView().showErrorDialog(
                                        AccountDetailsActivity.ERROR_CASE_EDIT_OPERATION
                                );
                            }
                        }, throwable -> {
                            throwable.printStackTrace();
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();
                                getView().showErrorDialog(
                                        AccountDetailsActivity.ERROR_CASE_EDIT_OPERATION
                                );
                            }
                        })
        );
    }

    @Override
    public void deleteOperation(int organizationId, int accountId, int operationId) {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        addSubscription(
                operationModel.deleteOrganizationOperation(organizationId, accountId, operationId)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();

                                if (response.isSuccessful()) {
                                    loadAccount(organizationId, accountId);
                                }
                            } else {
                                getView().showErrorDialog(
                                        AccountDetailsActivity.ERROR_CASE_EDIT_OPERATION
                                );
                            }
                        }, throwable -> {
                            throwable.printStackTrace();
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();
                                getView().showErrorDialog(
                                        AccountDetailsActivity.ERROR_CASE_EDIT_OPERATION
                                );
                            }
                        })
        );
    }
}