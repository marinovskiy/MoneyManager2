package alex.moneymanager.presenters;

import java.util.List;

import alex.moneymanager.entities.db.Account;
import alex.moneymanager.entities.db.Organization;
import alex.moneymanager.models.AccountModel;
import alex.moneymanager.models.OrganizationModel;
import alex.moneymanager.utils.PreferenceUtil;
import alex.moneymanager.views.MainView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainPresenterImpl extends AbstractPresenter<MainView> implements MainPresenter {

    private AccountModel accountModel;
    private OrganizationModel organizationModel;
    private PreferenceUtil preferenceUtil;

    public MainPresenterImpl(AccountModel accountModel, OrganizationModel organizationModel,
                             PreferenceUtil preferenceUtil) {
        this.accountModel = accountModel;
        this.organizationModel = organizationModel;
        this.preferenceUtil = preferenceUtil;
    }

    @Override
    public void loadMainDataApi() {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        addSubscription(
                accountModel.userAccountsApi()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            if (response.isSuccessful()) {
                                List<Account> accounts = response.body().getAccounts();

                                accountModel.saveAccountsDb(accounts);
                                loadOrganizationsApi(accounts);
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

    private void loadOrganizationsApi(List<Account> accounts) {
        addSubscription(
                organizationModel.userOrganizationsApi()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            if (response.isSuccessful()) {
                                List<Organization> organizations = response.body().getOrganizations();
                                organizationModel.saveOrganizationsDb(organizations);

                                if (isViewAttached()) {
                                    getView().dismissProgressDialog();
                                    getView().setData(accounts, organizations);
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

    @Override
    public void loadMainDataDb() {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        addSubscription(
                accountModel.userAccountsDb()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::loadOrganizationsDb, throwable -> {
                            throwable.printStackTrace();
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();
                                getView().showErrorDialog();
                            }
                        })
        );
    }

    private void loadOrganizationsDb(List<Account> accounts) {
        addSubscription(
                organizationModel.userOrganizationsDb()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(organizations -> {
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();
                                getView().setData(accounts, organizations);
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