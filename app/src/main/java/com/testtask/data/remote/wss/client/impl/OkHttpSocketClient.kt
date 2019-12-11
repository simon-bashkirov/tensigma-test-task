package com.testtask.data.remote.wss.client.impl

import com.testtask.data.remote.wss.client.SocketClient
import com.testtask.data.remote.wss.client.state.ConnectionState
import com.testtask.data.remote.wss.client.state.ConnectionState.*
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import okio.ByteString


class OkHttpSocketClient(wssBaseUrl: String) : SocketClient {

    private val client = OkHttpClient.Builder().build()
    private val request = Request.Builder().url(wssBaseUrl).build()

    private val webSocketProcessor = BehaviorProcessor.create<WebSocket>()

    private val connectionStateProcessor = BehaviorProcessor.create<ConnectionState>()

    private val messagePublisher = PublishProcessor.create<String>()

    private val listener = object : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: Response) {
            connectionStateProcessor.onNext(Connected)
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
            if (connectionStateProcessor.value is Closing) {
                connectionStateProcessor.onNext(
                    ClosedNormal
                )
            } else {
                connectionStateProcessor.onNext(ClosedError)
            }
        }
    }

    override fun connect() {
        webSocketProcessor.onNext(client.newWebSocket(request, listener))
        connectionStateProcessor.onNext(Connecting)
    }


    override fun disconnect() {
        webSocketProcessor.value?.close(1000, null)
        connectionStateProcessor.onNext(Closing)
    }

    override fun sendRawMessage(string: String) {
        webSocketProcessor.value?.send(string)
    }

    override fun getRawMessageStream() =
        (messagePublisher as Flowable<String>).subscribeOn(Schedulers.io())

    override fun connectionState() =
        (connectionStateProcessor as Flowable<ConnectionState>).subscribeOn(Schedulers.io())
}