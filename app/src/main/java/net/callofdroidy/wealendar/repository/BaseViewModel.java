package net.callofdroidy.wealendar.repository;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel extends AndroidViewModel {
    protected CompositeDisposable compositeDisposable;

    public BaseViewModel(Application application) {
        super(application);
        compositeDisposable = new CompositeDisposable();
    }

    public void clearSubscriptions() {
        compositeDisposable.clear();
    }
}
