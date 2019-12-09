package com.testtask.data.remote.wss.adapter.impl

import com.testtask.data.remote.wss.adapter.WebSocketAdapter
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.processors.PublishProcessor
import okhttp3.*
import okio.ByteString


class WebSocketAdapterImpl(wssBaseUrl: String) : WebSocketAdapter {

    private val client = OkHttpClient.Builder().build()
    private val request = Request.Builder().url(wssBaseUrl).build()

    private val webSocketProcessor = BehaviorProcessor.create<WebSocket>()

    private val connectionState = BehaviorProcessor.create<ConnectionState>()

    private val messagePublisher = PublishProcessor.create<String>()

    private val listener = object : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: Response) {
            connectionState.onNext(ConnectionState.Connected)
        }

        override fun onMessage(webSocket: WebSocket?, text: String?) {
            messagePublisher.onNext(text)
        }

        override fun onMessage(webSocket: WebSocket?, bytes: ByteString) {
            //ignore
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String?) {
            webSocket.close(code, reason)
            webSocket.cancel()
        }

        override fun onClosed(webSocket: WebSocket?, code: Int, reason: String?) {
            if (connectionState.value is ConnectionState.Closing) {
                connectionState.onNext(
                    ConnectionState.ClosedNormal
                )
            } else {
                connectionState.onNext(ConnectionState.ClosedError)
            }
        }
    }

    override fun connect() {
        webSocketProcessor.onNext(client.newWebSocket(request, listener))
        connectionState.onNext(ConnectionState.Connecting)
    }


    override fun disconnect() {
        webSocketProcessor.value?.close(1000, null)
        connectionState.onNext(ConnectionState.Closing)
    }

    override fun getMessageStream() = messagePublisher as Flowable<String>


    sealed class ConnectionState {
        object Connecting : ConnectionState()
        object Connected : ConnectionState()
        object Closing : ConnectionState()
        object ClosedNormal : ConnectionState()
        object ClosedError : ConnectionState()
    }
}