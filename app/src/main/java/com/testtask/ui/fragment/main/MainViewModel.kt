package com.testtask.ui.fragment.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.testtask.domain.interactor.CompletableInteractor
import com.testtask.domain.interactor.NoParams
import com.testtask.domain.interactor.auth.SignOutUseCase
import com.testtask.domain.interactor.transaction.ClearTransactionsUseCase
import com.testtask.domain.interactor.transaction.ObserveTransactionUpdatesUseCase
import com.testtask.domain.interactor.transaction.StartTransactionsUseCase
import com.testtask.domain.interactor.transaction.StopTransactionsUseCase
import com.testtask.domain.interactor.user.ObserveMyFirstProfileUseCase
import com.testtask.ui.mapper.ProfileMapper
import com.testtask.ui.mapper.TransactionMapper
import com.testtask.ui.model.ProfileShort
import com.testtask.ui.model.TransactionItem
import com.testtask.ui.state.ProgressState
import com.testtask.ui.viewmodel.DisposableViewModel
import io.reactivex.android.schedulers.AndroidSchedulers

class MainViewModel(

    observeMyFirstProfileUseCase: ObserveMyFirstProfileUseCase,

    observeTransactionUpdatesUseCase: ObserveTransactionUpdatesUseCase,

    private val startTransactionsUseCase: StartTransactionsUseCase,

    private val stopTransactionsUseCase: StopTransactionsUseCase,

    private val clearTransactionsUseCase: ClearTransactionsUseCase,

    private val signOutUseCase: SignOutUseCase

) : DisposableViewModel() {

    init {
        setProgressState(ProgressState.Progress)
        addDisposable(
            observeMyFirstProfileUseCase
                .execute(NoParams)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ optProfile ->
                    optProfile.value?.let {
                        setProgressState(ProgressState.Success)
                        profile.value = ProfileMapper.map(it)
                    }
                }, {
                    setProgressState(ProgressState.Error(it.message))
                })
        )
        addDisposable(
            observeTransactionUpdatesUseCase
                .execute(NoParams)
                .map { list -> list.map { TransactionMapper.map(it) } }
                .map { list -> list.sumByDouble { it.valueBtcOutput } to list }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ (totalValue, list) ->
                    _total.value = "%.8f BTC".format(totalValue)
                    _transactions.value = list
                }
                    , {
                        setProgressState(ProgressState.Error(it.message))
                    })
        )
    }


    private val profile = MutableLiveData<ProfileShort>()

    val firstName = Transformations.map(profile) {
        it.firstName
    }

    val lastName = Transformations.map(profile) { it.lastName }

    val email = Transformations.map(profile) { it.email }

    private val _transactions = MutableLiveData<List<TransactionItem>>()
    val transactions = _transactions as LiveData<List<TransactionItem>>

    private val _total = MutableLiveData<String>()
    val total = _total as LiveData<String>

    fun startButtonClicked() {
        processCompletableUseCase(startTransactionsUseCase)
    }

    fun stopButtonClicked() {
        processCompletableUseCase(stopTransactionsUseCase)
    }

    fun clearButtonClicked() {
        processCompletableUseCase(clearTransactionsUseCase)
    }

    fun signOutButtonClicked() {
        processCompletableUseCase(signOutUseCase)
    }

    private fun processCompletableUseCase(useCase: CompletableInteractor<NoParams>) {
        addDisposable(
            useCase
                .execute(NoParams)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )

    }

}
