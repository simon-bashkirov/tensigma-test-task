package com.testtask.data.remote.wss.model.message

import com.google.gson.annotations.SerializedName

enum class SocketMessageType {

    @SerializedName("utx")
    TRANSACTION,

    @SerializedName("block")
    BLOCK,

    @SerializedName("pong")
    PONG

}