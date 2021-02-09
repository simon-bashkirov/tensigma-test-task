package com.testtask.ui.main

import androidx.lifecycle.LiveData
import com.testtask.domain.interactor.NoParams
import com.testtask.domain.interactor.auth.ObserveAuthStateUseCase
import com.testtask.domain.model.user.AuthState
import com.testtask.ui.BaseViewModel
import com.testtask.utils.livedata.DistinctLiveData

class MainViewModel(observeAuthStateUse: ObserveAuthStateUseCase) :
    BaseViewModel() {

    private val _authStateLiveData = DistinctLiveData<AuthState>()
    val authStateLiveData = _authStateLiveData as LiveData<AuthState>

    init {
        _authStateLiveData.from(observeAuthStateUse(NoParams))
    }

}