package com.testtask.data.remote.wss.state

sealed class ConnectionState {
    object Connecting : ConnectionState()
    object Connected : ConnectionState()
    object Closing : ConnectionState()
    object ClosedNormal : ConnectionState()
    object ClosedError : ConnectionState()
}