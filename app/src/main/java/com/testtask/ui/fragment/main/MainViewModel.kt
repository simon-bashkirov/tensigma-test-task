package com.testtask.ui.fragment.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.testtask.domain.interactor.user.ObserveMyFirstProfileUseCase
import com.testtask.ui.model.ProfileShort
import com.testtask.ui.model.TransactionItem

class MainViewModel(private val observeMyFirstProfileUseCase: ObserveMyFirstProfileUseCase) :
    ViewModel() {

    private val profile = MutableLiveData<ProfileShort>()

    val firstName = Transformations.map(profile) { it.firstName }

    val lastName = Transformations.map(profile) { it.lastName }

    val email = Transformations.map(profile) { it.email }

    private val _transactions = MutableLiveData<List<TransactionItem>>()
    val transactions = _transactions as LiveData<List<TransactionItem>>

    private val _total = MutableLiveData<String>()
    val total = _total as LiveData<String>

    fun startButtonClicked() {
        Log.d("TAG", "start button clicked")
    }

    fun stopButtonClicked() {
        Log.d("TAG", "stop button clicked")
    }

    fun clearButtonClicked() {
        Log.d("TAG", "clear button clicked")
    }

    fun signOutButtonClicked() {
        Log.d("TAG", "sign out button clicked")
    }

}
