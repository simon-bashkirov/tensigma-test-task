package com.testtask.data.repository.user

import com.testtask.domain.model.user.UserInfo
import io.reactivex.Single

interface UserDataSource {

    fun getCurrentUser(): Single<UserInfo>

}