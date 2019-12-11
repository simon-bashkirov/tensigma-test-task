package com.testtask.data.remote.wss.model.command

import com.google.gson.annotations.SerializedName

data class BitcoinSocketCommand(

    @SerializedName("op")
    val type: SocketCommandType

)