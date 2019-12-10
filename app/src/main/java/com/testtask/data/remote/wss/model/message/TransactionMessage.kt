package com.testtask.data.remote.wss.model.message

import com.google.gson.annotations.SerializedName
import com.testtask.domain.model.transaction.Transaction

data class TransactionMessage (

    @SerializedName("op")
    val type: SocketMessageType,

    @SerializedName("x")
    val transaction: Transaction
)