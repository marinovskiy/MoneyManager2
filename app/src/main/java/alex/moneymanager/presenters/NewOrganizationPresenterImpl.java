package alex.moneymanager.presenters;

import alex.moneymanager.activities.NewOrganizationActivity;
import alex.moneymanager.entities.network.NetworkOrganization;
import alex.moneymanager.models.OrganizationModel;
import alex.moneymanager.views.NewOrganizationView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewOrganizationPresenterImpl extends AbstractPresenter<NewOrganizationView>
        implements NewOrganizationPresenter {

    private OrganizationModel organizationModel;

    public NewOrganizationPresenterImpl(OrganizationModel organizationModel) {
        this.organizationModel = organizationModel;
    }

    @Override
    public void addNewOrganization(NetworkOrganization organization) {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        addSubscription(
                organizationModel.newOrganization(organization)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            if (isViewAttached()) {
                                if (response.isSuccessful()) {
                                    organizationModel.saveOrganizationDb(
                                            response.body().getOrganization()
                                    );

                                    getView().organizationAddedSuccess();
                                } else {
                                    getView().dismissProgressDialog();
                                    getView().showErrorDialog(
                                            NewOrganizationActivity.ERROR_CASE_NEW_ORGANIZATION
                                    );
                                }
                            }
                        }, throwable -> {
                            throwable.printStackTrace();
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();
                                getView().showErrorDialog(
                                        NewOrganizationActivity.ERROR_CASE_NEW_ORGANIZATION
                                );
                            }
                        })
        );
    }
}