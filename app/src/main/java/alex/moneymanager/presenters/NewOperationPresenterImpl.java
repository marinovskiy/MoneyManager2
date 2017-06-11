package alex.moneymanager.presenters;

import java.util.List;

import alex.moneymanager.activities.NewOperationActivity;
import alex.moneymanager.entities.db.Category;
import alex.moneymanager.entities.network.NetworkOperation;
import alex.moneymanager.models.CategoryModel;
import alex.moneymanager.models.OperationModel;
import alex.moneymanager.utils.SystemUtils;
import alex.moneymanager.views.NewOperationView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewOperationPresenterImpl extends AbstractPresenter<NewOperationView>
        implements NewOperationPresenter {

    private SystemUtils systemUtils;
    private CategoryModel categoryModel;
    private OperationModel operationModel;

    public NewOperationPresenterImpl(SystemUtils systemUtils, CategoryModel categoryModel,
                                     OperationModel operationModel) {
        this.systemUtils = systemUtils;
        this.categoryModel = categoryModel;
        this.operationModel = operationModel;
    }

    @Override
    public void loadCategories() {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        if (systemUtils.isConnected()) {
            addSubscription(
                    categoryModel.categoriesApi()
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(response -> {
                                if (response.isSuccessful()) {
                                    List<Category> categories = response.body().getCategories();
                                    categoryModel.saveCategoriesDb(categories);

                                    if (isViewAttached()) {
                                        getView().dismissProgressDialog();
                                        getView().setCategories(categories);
                                    }
                                } else {
                                    if (isViewAttached()) {
                                        getView().dismissProgressDialog();
                                        getView().showErrorDialog(
                                                NewOperationActivity.ERROR_CASE_CATEGORIES
                                        );
                                    }
                                }
                            }, throwable -> {
                                throwable.printStackTrace();
                                if (isViewAttached()) {
                                    getView().dismissProgressDialog();
                                    getView().showErrorDialog(
                                            NewOperationActivity.ERROR_CASE_CATEGORIES
                                    );
                                }
                            })
            );
        } else {
            addSubscription(
                    categoryModel.categoriesDb()
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(categories -> {
                                if (isViewAttached()) {
                                    getView().dismissProgressDialog();
                                    getView().setCategories(categories);
                                }
                            }, throwable -> {
                                throwable.printStackTrace();
                                if (isViewAttached()) {
                                    getView().dismissProgressDialog();
                                    getView().showErrorDialog(
                                            NewOperationActivity.ERROR_CASE_CATEGORIES
                                    );
                                }
                            })
            );
        }
    }

    @Override
    public void addNewOperation(int accountId, NetworkOperation operation) {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        addSubscription(
                operationModel.newOperation(accountId, operation)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            if (isViewAttached()) {
                                if (response.isSuccessful()) {
                                    getView().operationAddedSuccess();
                                } else {
                                    getView().dismissProgressDialog();
                                    getView().showErrorDialog(
                                            NewOperationActivity.ERROR_CASE_NEW_OPERATION
                                    );
                                }
                            }
                        }, throwable -> {
                            throwable.printStackTrace();
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();
                                getView().showErrorDialog(
                                        NewOperationActivity.ERROR_CASE_NEW_OPERATION
                                );
                            }
                        })
        );
    }

    @Override
    public void loadOperationDb(int operationId) {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        addSubscription(
                operationModel.operationByIdDb(operationId)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(operation -> {
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();
                                getView().setOperation(operation);
                            }
                        }, throwable -> {
                            throwable.printStackTrace();
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();
                                getView().showErrorDialog(
                                        NewOperationActivity.ERROR_CASE_LOAD_OPERATION
                                );
                            }
                        })
        );
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
                                if (response.isSuccessful()) {
                                    getView().operationAddedSuccess();
                                } else {
                                    getView().dismissProgressDialog();
                                    getView().showErrorDialog(
                                            NewOperationActivity.ERROR_CASE_EDIT_OPERATION
                                    );
                                }
                            }
                        }, throwable -> {
                            throwable.printStackTrace();
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();
                                getView().showErrorDialog(
                                        NewOperationActivity.ERROR_CASE_EDIT_OPERATION
                                );
                            }
                        })
        );
    }

    @Override
    public void addNewOrganizationOperation(int organizationId, int accountId,
                                            NetworkOperation operation) {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        addSubscription(
                operationModel.newOrganizationOperation(organizationId, accountId, operation)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            if (isViewAttached()) {
                                if (response.isSuccessful()) {
                                    getView().operationAddedSuccess();
                                } else {
                                    getView().dismissProgressDialog();
                                    getView().showErrorDialog(
                                            NewOperationActivity.ERROR_CASE_NEW_OPERATION
                                    );
                                }
                            }
                        }, throwable -> {
                            throwable.printStackTrace();
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();
                                getView().showErrorDialog(
                                        NewOperationActivity.ERROR_CASE_NEW_OPERATION
                                );
                            }
                        })
        );
    }

    @Override
    public void editOrganizationOperation(int organizationId, int accountId, int operationId,
                                          NetworkOperation operation) {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        addSubscription(
                operationModel.editOrganizationOperation(organizationId, accountId, operationId, operation)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            if (isViewAttached()) {
                                if (response.isSuccessful()) {
                                    getView().operationAddedSuccess();
                                } else {
                                    getView().dismissProgressDialog();
                                    getView().showErrorDialog(
                                            NewOperationActivity.ERROR_CASE_EDIT_OPERATION
                                    );
                                }
                            }
                        }, throwable -> {
                            throwable.printStackTrace();
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();
                                getView().showErrorDialog(
                                        NewOperationActivity.ERROR_CASE_EDIT_OPERATION
                                );
                            }
                        })
        );
    }
}