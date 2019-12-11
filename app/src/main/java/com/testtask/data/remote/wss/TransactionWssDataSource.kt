package com.testtask.data.remote.wss

import android.annotation.SuppressLint
import com.testtask.data.remote.wss.mapper.SocketMapperFactory
import com.testtask.data.remote.wss.model.command.BitcoinSocketCommand
import com.testtask.data.remote.wss.model.command.SocketCommandType.SUBSCRIBE
import com.testtask.data.remote.wss.model.command.SocketCommandType.UNSUBSCRIBE
import com.testtask.data.remote.wss.model.message.BitcoinSocketMessage
import com.testtask.data.remote.wss.model.message.SocketMessageType
import com.testtask.data.remote.wss.model.message.SocketMessageType.BLOCK
import com.testtask.data.remote.wss.model.message.SocketMessageType.TRANSACTION
import com.testtask.data.remote.wss.service.SocketServiceFactory
import com.testtask.data.repository.transaction.TransactionDataSource
import com.testtask.domain.model.transaction.Transaction
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor

class TransactionWssDataSource(

    serviceFactory: SocketServiceFactory,

    mapperFactory: SocketMapperFactory

) : TransactionDataSource {

    private val transactionPublisher = PublishProcessor.create<Transaction>()

    private val service = serviceFactory.createSocketService(
        BitcoinSocketCommand::class.java, BitcoinSocketMessage::class.java
    )

    private val transactionMapper = mapperFactory.createMessageMapper(Transaction::class.java)

    init {
        subscribeToStream()
    }

    override fun startTransactionStream() = Completable.fromCallable {
        service.sendCommand(BitcoinSocketCommand(SUBSCRIBE))
    }

    override fun stopTransactionStream() = Completable.fromCallable {
        service.sendCommand(BitcoinSocketCommand(UNSUBSCRIBE))
    }

    override fun getTransactionStream() = transactionPublisher as Flowable<Transaction>

    @SuppressLint("CheckResult")
    private fun subscribeToStream() {
        service.getMessageStream()
            .subscribe { message ->
                when (message.type) {
                    TRANSACTION -> {
                        message.payload
                            ?.let { transactionMapper.map(it.toString()).value }
                            ?.let { transactionPublisher.onNext(it) }
                    }
                    BLOCK -> {/*Do nothing*/
                    }
                    SocketMessageType.PONG -> {/*TODO*/
                    }
                }

            }
    }


}