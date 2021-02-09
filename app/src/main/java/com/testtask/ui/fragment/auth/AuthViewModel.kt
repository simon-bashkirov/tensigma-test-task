package com.testtask.ui.fragment.auth

import com.testtask.domain.interactor.auth.SignInUseCase
import com.testtask.domain.interactor.auth.SignInUseCase.Params
import com.testtask.ui.BaseViewModel
import com.testtask.utils.livedata.DistinctLiveData

class AuthViewModel(
    private val signInUseCase: SignInUseCase
) : BaseViewModel() {

    val email = DistinctLiveData<String>()

    val password = DistinctLiveData<String>()

    fun signInButtonClicked() {
        signInUseCase(
            Params(
                email.value ?: return,
                password.value ?: return
            )
        ).subscribeUi()
    }

}
