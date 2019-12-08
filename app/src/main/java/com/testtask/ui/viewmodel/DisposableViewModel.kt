package com.testtask.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.testtask.ui.state.ProgressState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class DisposableViewModel : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _progressStateLiveData = MutableLiveData<ProgressState>()
    val progressStateLiveData = _progressStateLiveData as LiveData<ProgressState>

    val showProgressLiveData = Transformations.map(progressStateLiveData) {
        it == ProgressState.Progress
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    protected fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    protected fun setProgressState(progressState: ProgressState) {
        _progressStateLiveData.value = progressState
    }
}