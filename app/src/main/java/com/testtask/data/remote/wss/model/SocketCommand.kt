package com.testtask.data.remote.wss.model

import com.google.gson.annotations.SerializedName

enum class SocketCommand {

    @SerializedName("unconfirmed_sub")
    SUBSCRIBE,

    @SerializedName("unconfirmed_unsub")
    UNSUBSCRIBE

}