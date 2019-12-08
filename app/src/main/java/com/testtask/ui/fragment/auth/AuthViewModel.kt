package com.testtask.ui.fragment.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import com.testtask.domain.interactor.auth.SignInUseCase
import com.testtask.domain.interactor.auth.SignOutUseCase
import com.testtask.ui.livedata.ValueChangedLiveData

class AuthViewModel(

    private val signInUseCase: SignInUseCase,

    private val signOutUseCase: SignOutUseCase

) : ViewModel() {

    val eMailLiveData = ValueChangedLiveData<String>()

    val passWordLiveData = ValueChangedLiveData<String>()

    fun signInButtonClicked() {
        Log.d("TAG", "email: ${eMailLiveData.value} password: ${passWordLiveData.value}")
    }

}
