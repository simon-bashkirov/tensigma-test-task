package com.testtask.ui.activity

import androidx.lifecycle.ViewModel
import com.testtask.domain.interactor.auth.ObserveAuthStateUseCase

class MainActivityViewModel(private val observeAuthStateUse: ObserveAuthStateUseCase) : ViewModel()