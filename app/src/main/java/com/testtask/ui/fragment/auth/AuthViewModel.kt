package com.testtask.ui.fragment.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import com.testtask.ui.livedata.ValueChangedLiveData

class AuthViewModel : ViewModel() {

    val eMailLiveData = ValueChangedLiveData<String>()

    val passWordLiveData = ValueChangedLiveData<String>()

    fun signInButtonClicked() {
        Log.d("TAG", "email: ${eMailLiveData.value} password: ${passWordLiveData.value}")
    }

}
