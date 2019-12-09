package com.testtask.ui.mapper

import com.testtask.domain.mapper.Mapper
import com.testtask.domain.model.transaction.Transaction
import com.testtask.ui.model.TransactionItem
import java.text.SimpleDateFormat

object TransactionMapper : Mapper<Transaction, TransactionItem> {

    private val simpleDateFormat = SimpleDateFormat.getTimeInstance()

    override fun map(source: Transaction) =
        source.run {
            TransactionItem(
                hashOutput = this.x?.hash ?: "n/a",
                timeOutput = this.x?.time?.let { simpleDateFormat.format(it) } ?: "n/a",
                //TODO get it clear
                valueBtcOutput = "xz",
                valueUsdOutput = "xz"
            )
        }
}