package com.testtask.domain.repository

import com.testtask.domain.model.user.UserInfo
import io.reactivex.Flowable

interface UserRepository {

    fun getCurrentUserInfo(): Flowable<UserInfo>

}
