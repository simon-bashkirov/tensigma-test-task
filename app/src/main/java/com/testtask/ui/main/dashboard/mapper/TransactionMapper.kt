package com.testtask.ui.main.dashboard.mapper

import com.testtask.domain.mapper.Mapper
import com.testtask.domain.model.transaction.Transaction
import com.testtask.ui.main.dashboard.model.TransactionItem
import java.text.SimpleDateFormat

object TransactionMapper : Mapper<Transaction, TransactionItem> {

    private val simpleDateFormat = SimpleDateFormat.getTimeInstance()

    override fun map(source: Transaction) =
        source.run {
            TransactionItem(

                hashOutput = hash,

                timeOutput = simpleDateFormat.format(time),

                valueBtcOutput = out?.sumByDouble { it.value.toDouble().div(100_000_000) } ?: 0.0
            )
        }
}