package com.testtask.ui.main.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.testtask.domain.interactor.NoParams
import com.testtask.domain.interactor.auth.SignOutUseCase
import com.testtask.domain.interactor.transaction.ClearTransactionsUseCase
import com.testtask.domain.interactor.transaction.ObserveTransactionUpdatesUseCase
import com.testtask.domain.interactor.transaction.StartTransactionsUseCase
import com.testtask.domain.interactor.transaction.StopTransactionsUseCase
import com.testtask.domain.interactor.user.ObserveMyFirstProfileUseCase
import com.testtask.domain.model.user.Profile
import com.testtask.ui.BaseViewModel
import com.testtask.ui.main.dashboard.mapper.ProfileMapper
import com.testtask.ui.main.dashboard.mapper.TransactionMapper
import com.testtask.ui.main.dashboard.model.ProfileShort
import com.testtask.ui.main.dashboard.model.TransactionItem
import com.testtask.utils.lang.Optional

class DashboardViewModel(

    observeMyFirstProfileUseCase: ObserveMyFirstProfileUseCase,

    observeTransactionUpdatesUseCase: ObserveTransactionUpdatesUseCase,

    private val startTransactionsUseCase: StartTransactionsUseCase,

    private val stopTransactionsUseCase: StopTransactionsUseCase,

    private val clearTransactionsUseCase: ClearTransactionsUseCase,

    private val signOutUseCase: SignOutUseCase

) : BaseViewModel() {

    init {
        observeMyFirstProfileUseCase(NoParams)
            .subscribeUi(onSuccess = { optProfile: Optional<Profile> ->
                profile.value = optProfile.value?.let { ProfileMapper.map(it) }
            })
        observeTransactionUpdatesUseCase(NoParams)
            .map { list -> list.map { TransactionMapper.map(it) } }
            .map { list -> list.sumByDouble { it.valueBtcOutput } to list }
            .subscribeUi(onSuccess = { (totalValue, list) ->
                _total.value = "%.8f BTC".format(totalValue)
                _transactions.value = list
            })
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
        startTransactionsUseCase(NoParams).subscribeUi()
    }

    fun stopButtonClicked() {
        stopTransactionsUseCase(NoParams).subscribeUi()
    }

    fun clearButtonClicked() {
        clearTransactionsUseCase(NoParams).subscribeUi()
    }

    fun signOutButtonClicked() {
        signOutUseCase(NoParams).subscribeUi()
    }

}
