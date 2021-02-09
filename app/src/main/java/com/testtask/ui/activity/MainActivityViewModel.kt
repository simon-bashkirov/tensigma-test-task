package com.testtask.ui.activity

import androidx.lifecycle.LiveData
import com.testtask.domain.interactor.NoParams
import com.testtask.domain.interactor.auth.ObserveAuthStateUseCase
import com.testtask.domain.state.AuthState
import com.testtask.ui.base.BaseViewModel
import com.testtask.utils.livedata.DistinctLiveData

class MainActivityViewModel(observeAuthStateUse: ObserveAuthStateUseCase) :
    BaseViewModel() {

    private val _authStateLiveData = DistinctLiveData<AuthState>()
    val authStateLiveData = _authStateLiveData as LiveData<AuthState>

    init {
        _authStateLiveData.from(observeAuthStateUse(NoParams))
    }

}