package com.testtask.ui.activity

import androidx.lifecycle.LiveData
import com.testtask.domain.interactor.NoParams
import com.testtask.domain.interactor.auth.ObserveAuthStateUseCase
import com.testtask.domain.state.AuthState
import com.testtask.ui.livedata.ValueChangedLiveData
import com.testtask.ui.viewmodel.DisposableViewModel
import io.reactivex.android.schedulers.AndroidSchedulers

class MainActivityViewModel(private val observeAuthStateUse: ObserveAuthStateUseCase) :
    DisposableViewModel() {
    init {
        addDisposable(
            observeAuthStateUse.execute(NoParams)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _authStateLiveData.setValue(it)
                },
                    {
                        //TODO show error
                    })
        )
    }

    private val _authStateLiveData = ValueChangedLiveData<AuthState>()
    val authStateLiveData = _authStateLiveData as LiveData<AuthState>

}