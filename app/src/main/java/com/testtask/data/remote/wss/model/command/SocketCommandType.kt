package com.testtask.data.remote.wss.model.command

import com.google.gson.annotations.SerializedName

enum class SocketCommandType {

    @SerializedName("unconfirmed_sub")
    SUBSCRIBE,

    @SerializedName("unconfirmed_unsub")
    UNSUBSCRIBE,

    @SerializedName("ping")
    PING
}