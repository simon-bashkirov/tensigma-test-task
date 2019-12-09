package com.testtask.ui.fragment.auth

import com.testtask.domain.interactor.auth.SignInUseCase
import com.testtask.domain.interactor.auth.SignInUseCase.Params
import com.testtask.ui.livedata.ValueChangedLiveData
import com.testtask.ui.state.ProgressState
import com.testtask.ui.viewmodel.DisposableViewModel
import io.reactivex.android.schedulers.AndroidSchedulers

class AuthViewModel(

    private val signInUseCase: SignInUseCase

) : DisposableViewModel() {

    val eMailLiveData = ValueChangedLiveData<String>()

    val passWordLiveData = ValueChangedLiveData<String>()

    fun signInButtonClicked() {
        setProgressState(ProgressState.Progress)
        addDisposable(
            signInUseCase.execute(
                Params(
                    eMailLiveData.value ?: "",
                    passWordLiveData.value ?: ""
                )
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setProgressState(ProgressState.Success)
                }, {
                    setProgressState(ProgressState.Error(it.message))
                })
        )
    }

}
