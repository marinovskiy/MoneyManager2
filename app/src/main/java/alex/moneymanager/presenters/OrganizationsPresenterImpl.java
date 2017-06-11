package alex.moneymanager.presenters;

import alex.moneymanager.entities.db.Organization;
import alex.moneymanager.entities.network.NetworkAccount;
import alex.moneymanager.fragments.OrganizationsFragment;
import alex.moneymanager.models.AccountModel;
import alex.moneymanager.models.OrganizationModel;
import alex.moneymanager.utils.SystemUtils;
import alex.moneymanager.views.OrganizationsView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OrganizationsPresenterImpl extends AbstractPresenter<OrganizationsView>
        implements OrganizationsPresenter {

    private SystemUtils systemUtils;
    private OrganizationModel organizationModel;
    private AccountModel accountModel;

    public OrganizationsPresenterImpl(SystemUtils systemUtils, OrganizationModel organizationModel,
                                      AccountModel accountModel) {
        this.systemUtils = systemUtils;
        this.organizationModel = organizationModel;
        this.accountModel = accountModel;
    }

    @Override
    public void loadOrganization(int organizationId) {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        if (systemUtils.isConnected()) {
            addSubscription(
                    organizationModel.organizationByIdApi(organizationId)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(response -> {
                                if (response.isSuccessful()) {
                                    Organization organization = response.body().getOrganization();
                                    organizationModel.saveOrganizationDb(organization);

                                    if (isViewAttached()) {
                                        getView().dismissProgressDialog();
                                        getView().setOrganization(organization);
                                    }
                                } else {
                                    if (isViewAttached()) {
                                        getView().dismissProgressDialog();
                                        getView().showErrorDialog(
                                                OrganizationsFragment.ERROR_CASE_ORGANIZATION
                                        );
                                    }
                                }
                            }, throwable -> {
                                throwable.printStackTrace();
                                if (isViewAttached()) {
                                    getView().dismissProgressDialog();
                                    getView().showErrorDialog(
                                            OrganizationsFragment.ERROR_CASE_ORGANIZATION
                                    );
                                }
                            })
            );
        } else {
            addSubscription(
                    organizationModel.organizationByIdDb(organizationId)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(organization -> {
                                if (isViewAttached()) {
                                    getView().dismissProgressDialog();
                                    getView().setOrganization(organization);
                                }
                            }, throwable -> {
                                throwable.printStackTrace();
                                if (isViewAttached()) {
                                    getView().dismissProgressDialog();
                                    getView().showErrorDialog(
                                            OrganizationsFragment.ERROR_CASE_ORGANIZATION
                                    );
                                }
                            })
            );
        }
    }

    @Override
    public void deleteAccount(int organizationId, int accountId) {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        addSubscription(
                accountModel.deleteOrganizationAccount(organizationId, accountId)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();

                                if (response.isSuccessful()) {
                                    loadOrganization(organizationId);
                                }
                            } else {
                                getView().showErrorDialog(
                                        OrganizationsFragment.ERROR_CASE_DELETE_ACCOUNT
                                );
                            }
                        }, throwable -> {
                            throwable.printStackTrace();
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();
                                getView().showErrorDialog(
                                        OrganizationsFragment.ERROR_CASE_DELETE_ACCOUNT
                                );
                            }
                        })
        );
    }
}