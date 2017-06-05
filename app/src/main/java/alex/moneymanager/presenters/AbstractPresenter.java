package alex.moneymanager.presenters;

import alex.moneymanager.views.BaseView;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class AbstractPresenter<V extends BaseView> implements BasePresenter<V> {

    private V view;
    private CompositeDisposable compositeDisposable;

    public AbstractPresenter() {
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @Override
    public boolean isViewAttached() {
        return view != null;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public V getView() {
        return view;
    }

    @Override
    public void addSubscription(Disposable disposable) {
        compositeDisposable.add(disposable);
    }
}