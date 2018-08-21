package net.callofdroidy.wealendar.repository;

import android.arch.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel extends ViewModel{
    protected CompositeDisposable compositeDisposable;

    public BaseViewModel() {
        compositeDisposable = new CompositeDisposable();
    }

    public void clearSubscriptions() {
        compositeDisposable.clear();
    }
}
