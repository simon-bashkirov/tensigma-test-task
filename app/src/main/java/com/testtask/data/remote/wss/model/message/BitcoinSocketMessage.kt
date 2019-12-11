package com.testtask.data.remote.wss.model.message

import com.google.gson.annotations.SerializedName

data class BitcoinSocketMessage(

    @SerializedName("op")
    val type: SocketMessageType,

    @SerializedName("x")
    val payload: Any?

)