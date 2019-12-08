package com.testtask.data.remote.rest.model.response

import com.google.gson.annotations.SerializedName
import com.testtask.domain.model.user.UserInfo

data class UserInfoResponse(
    @SerializedName("info")
    val userInfo: UserInfo
)