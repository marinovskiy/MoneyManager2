package alex.moneymanager.views;

import java.util.List;

import alex.moneymanager.entities.db.Category;

public interface NewOperationView extends BaseView {

    void setCategories(List<Category> categories);

    void operationAddedSuccess();

    void showProgressDialog();

    void dismissProgressDialog();

    void showErrorDialog(int errorCase);

}