package com.testtask.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.testtask.ui.state.ProgressState
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class BaseViewModel : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _progressState = MutableLiveData<ProgressState>()
    val progressState = _progressState as LiveData<ProgressState>

    val showProgress = Transformations.map(progressState) {
        it == ProgressState.Progress
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    private fun setProgressState(progressState: ProgressState) {
        _progressState.value = progressState
    }

    /**
     * Subscribes this LiveData to flowable source and sets value from result. Subscriptions is disposed on AppViewModel clear.
     * @param onSuccess optional task to be done in addition to setting LiveData value
     * @param onError optional custom error handling
     */
    protected fun <T> MutableLiveData<T>.from(
        source: Flowable<T>,
        onSuccess: (T) -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        source.subscribeUi({
            value = it
            onSuccess(it)
        }, {
            onError(it)
        })
    }

    /**
     * Subscription for flowable source with result processed on MainThread. Disposes on AppViewModel clear.
     * Sets progress state on subscribe and clears progress on success or error.
     * @param onSuccess optional task to be done with result
     * @param onError optional custom error handling in addition to AppViewModel.handleError()
     */
    protected fun <T> Flowable<T>.subscribeUi(
        onSuccess: (T) -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        setProgressState(ProgressState.Progress)
        subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setProgressState(ProgressState.Success)
                onSuccess(it)
            }, {
                setProgressState(ProgressState.Error(it.message))
                // handleError(it)
                onError(it)
            })
            .untilCleared()
    }

    /**
     * Subscription for completable source with result processed on MainThread. Disposes on AppViewModel clear.
     * Sets progress state on subscribe and clears progress on success or error.
     * @param onComplete optional task to be done on complete
     * @param onError optional custom error handling in addition to AppViewModel.handleError()
     */
    protected fun Completable.subscribeUi(
        onComplete: () -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        setProgressState(ProgressState.Progress)
        subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setProgressState(ProgressState.Success)
                onComplete()
            }, {
                setProgressState(ProgressState.Error(it.message))
                // handleError(it)
                onError(it)
            })
            .untilCleared()
    }

    private fun Disposable.untilCleared() = disposables.add(this)
}