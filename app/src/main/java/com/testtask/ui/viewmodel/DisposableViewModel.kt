package com.testtask.ui.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class DisposableViewModel : ViewModel() {

    private val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    protected fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}