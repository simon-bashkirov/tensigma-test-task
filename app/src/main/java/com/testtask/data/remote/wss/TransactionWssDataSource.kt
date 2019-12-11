package com.testtask.data.remote.wss

import android.annotation.SuppressLint
import com.testtask.data.remote.wss.client.state.ConnectionState
import com.testtask.data.remote.wss.mapper.SocketMapperFactory
import com.testtask.data.remote.wss.model.command.BitcoinSocketCommand
import com.testtask.data.remote.wss.model.command.SocketCommandType
import com.testtask.data.remote.wss.model.message.BitcoinSocketMessage
import com.testtask.data.remote.wss.model.message.SocketMessageType.*
import com.testtask.data.remote.wss.service.SocketServiceFactory
import com.testtask.data.repository.transaction.TransactionDataSource
import com.testtask.domain.model.transaction.Transaction
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong

class TransactionWssDataSource(

    serviceFactory: SocketServiceFactory,

    mapperFactory: SocketMapperFactory

) : TransactionDataSource {

    private val transactionPublisher = PublishProcessor.create<Transaction>()

    private val service = serviceFactory.createSocketService(
        BitcoinSocketCommand::class.java, BitcoinSocketMessage::class.java
    )

    private val transactionMapper = mapperFactory.createMessageMapper(Transaction::class.java)

    private val pingExecutor = Executors.newSingleThreadScheduledExecutor()

    private val lastMessageTime = AtomicLong(0)

    private val started = AtomicBoolean(false)

    private val pingSent = AtomicBoolean(false)

    init {
        service.connect()
        schedulePing()
        subscribeToStream()
        subscribeToConnectionState()
    }


    override fun startTransactionStream() = Completable.fromCallable {
        started.set(true)
        service.sendCommand(SUBSCRIBE)
    }

    override fun stopTransactionStream() = Completable.fromCallable {
        started.set(false)
        service.sendCommand(UNSUBSCRIBE)
    }

    override fun getTransactionStream() = transactionPublisher as Flowable<Transaction>

    @SuppressLint("CheckResult")
    private fun subscribeToStream() {
        service.getMessageStream()
            .subscribe({ message ->
                when (message.type) {
                    TRANSACTION -> handleTransactionMessage(message)
                    BLOCK -> {/*Do nothing*/
                    }
                    PONG -> handlePong()
                }
            }, {
                //TODO error handling
            })
    }

    @SuppressLint("CheckResult")
    private fun subscribeToConnectionState() {
        service.connectionState()
            .subscribe({
                if (it is ConnectionState.Connected && started.get()) {
                    service.sendCommand(SUBSCRIBE)
                }
            }, {
                //TODO error handling
            })
    }

    private fun handleTransactionMessage(message: BitcoinSocketMessage) {
        message.payload
            ?.let { transactionMapper.map(it.toString()).value }
            ?.let { transactionPublisher.onNext(it) }
    }

    private fun handlePong() {
        if (started.get()) {
            service.sendCommand(SUBSCRIBE)
        }
    }

    private fun schedulePing() {
        pingExecutor.scheduleAtFixedRate({
            val now = System.currentTimeMillis()
            val last = lastMessageTime.get()
            if (started.get() && now - last > TIMEOUT) {
                if (!pingSent.get()) {
                    service.sendCommand(PING)
                    pingSent.set(true)
                } else {
                    service.disconnect()
                    service.connect()
                }

            }
        }, PING_RATE, PING_RATE, TimeUnit.SECONDS)
    }

    companion object {
        private const val PING_RATE = 3L
        private const val TIMEOUT = 5L

        private val SUBSCRIBE = BitcoinSocketCommand(SocketCommandType.SUBSCRIBE)
        private val UNSUBSCRIBE = BitcoinSocketCommand(SocketCommandType.UNSUBSCRIBE)
        private val PING = BitcoinSocketCommand(SocketCommandType.PING)
    }

}