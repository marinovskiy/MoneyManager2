package alex.moneymanager.presenters;

import alex.moneymanager.activities.AddMembersActivity;
import alex.moneymanager.models.OrganizationModel;
import alex.moneymanager.models.UserModel;
import alex.moneymanager.views.OrganizationAddMemberView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OrganizationAddMemberPresenterImpl extends AbstractPresenter<OrganizationAddMemberView>
        implements OrganizationAddMemberPresenter {

    private UserModel userModel;
    private OrganizationModel organizationModel;

    public OrganizationAddMemberPresenterImpl(UserModel userModel,
                                              OrganizationModel organizationModel) {
        this.userModel = userModel;
        this.organizationModel = organizationModel;
    }

    @Override
    public void searchUsers(String email) {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        addSubscription(
                userModel.searchUsersByEmail(email)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();

                                if (response.isSuccessful()) {
                                    getView().setSearchResults(response.body().getUsers());
                                } else {
                                    getView().showErrorDialog(
                                            AddMembersActivity.ERROR_CASE_ADD_MEMBER
                                    );
                                }
                            }
                        }, throwable -> {
                            throwable.printStackTrace();
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();
                                getView().showErrorDialog(
                                        AddMembersActivity.ERROR_CASE_ADD_MEMBER
                                );
                            }
                        })
        );
    }

    @Override
    public void addMember(int organizationId, int memberId) {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        addSubscription(
                organizationModel.addMember(organizationId, memberId)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();

                                if (response.isSuccessful()) {
                                    getView().memberAddedSuccessful();
                                } else if (response.code() == 409) {
                                    getView().alreadyMember();
                                } else {
                                    getView().showErrorDialog(
                                            AddMembersActivity.ERROR_CASE_ADD_MEMBER
                                    );
                                }
                            }
                        }, throwable -> {
                            throwable.printStackTrace();
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();
                                getView().showErrorDialog(
                                        AddMembersActivity.ERROR_CASE_ADD_MEMBER
                                );
                            }
                        })
        );
    }
}