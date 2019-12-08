package com.testtask.data.remote.rest.mapper

import com.testtask.data.remote.rest.model.response.UserInfoResponse
import com.testtask.domain.mapper.Mapper
import com.testtask.domain.model.user.UserInfo

object UserInfoResponseMapper : Mapper<UserInfoResponse, UserInfo> {
    override fun map(source: UserInfoResponse) = source.userInfo

}