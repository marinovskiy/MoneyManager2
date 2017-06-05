package alex.moneymanager.presenters;

import alex.moneymanager.views.BaseView;
import io.reactivex.disposables.Disposable;

public interface BasePresenter<V extends BaseView> {

    void attachView(V view);

    boolean isViewAttached();

    void detachView();

    V getView();

    void addSubscription(Disposable disposable);
}