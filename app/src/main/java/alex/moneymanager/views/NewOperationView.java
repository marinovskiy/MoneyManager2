package alex.moneymanager.views;

import java.util.List;

import alex.moneymanager.entities.db.Category;
import alex.moneymanager.entities.db.Operation;

public interface NewOperationView extends BaseView {

    void setCategories(List<Category> categories);

    void operationAddedSuccess();

    void setOperation(Operation operation);

    void showProgressDialog();

    void dismissProgressDialog();

    void showErrorDialog(int errorCase);

}