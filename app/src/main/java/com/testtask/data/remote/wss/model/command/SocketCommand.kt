package com.testtask.data.remote.wss.model.command

import com.google.gson.annotations.SerializedName

data class SocketCommand(

    @SerializedName("op")
    val type: SocketCommandType

)