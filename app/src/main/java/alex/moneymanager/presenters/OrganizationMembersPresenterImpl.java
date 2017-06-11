package alex.moneymanager.presenters;

import alex.moneymanager.models.OrganizationModel;
import alex.moneymanager.views.OrganizationMembersView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OrganizationMembersPresenterImpl extends AbstractPresenter<OrganizationMembersView>
        implements OrganizationMembersPresenter {

    private OrganizationModel organizationModel;

    public OrganizationMembersPresenterImpl(OrganizationModel organizationModel) {
        this.organizationModel = organizationModel;
    }

    @Override
    public void loadMembersApi(int organizationId) {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        addSubscription(
                organizationModel.organizationByIdApi(organizationId)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            if (response.isSuccessful()) {
                                organizationModel.saveOrganizationDb(response.body().getOrganization());

                                if (isViewAttached()) {
                                    getView().dismissProgressDialog();
                                    getView().setMembers(response.body().getOrganization());
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
    public void loadMembersDb(int organizationId) {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        addSubscription(
                organizationModel.organizationByIdDb(organizationId)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(organization -> {
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();
                                getView().setMembers(organization);
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