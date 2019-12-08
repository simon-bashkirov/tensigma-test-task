package com.testtask.ui.state

sealed class ProgressState {

    object Progress : ProgressState()

    object Success : ProgressState()

    class Error(val message: String?) : ProgressState()
}