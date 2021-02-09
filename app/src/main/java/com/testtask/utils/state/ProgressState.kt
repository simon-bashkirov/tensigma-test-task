package com.testtask.utils.state

sealed class ProgressState {

    object Progress : ProgressState()

    object Success : ProgressState()

    class Error(val message: String?) : ProgressState()
}