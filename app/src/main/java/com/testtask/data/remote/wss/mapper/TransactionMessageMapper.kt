package com.testtask.data.remote.wss.mapper

import com.testtask.data.remote.wss.model.message.TransactionMessage
import com.testtask.domain.mapper.Mapper
import com.testtask.domain.model.transaction.Transaction

object TransactionMessageMapper : Mapper<TransactionMessage, Transaction> {

    override fun map(source: TransactionMessage) = source.transaction
}