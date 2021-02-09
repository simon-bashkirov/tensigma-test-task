package com.testtask.domain.repository

import com.testtask.domain.model.user.UserInfo
import com.testtask.utils.lang.Optional
import io.reactivex.Completable
import io.reactivex.Flowable

interface UserRepository {

    fun getCurrentUserInfo(): Flowable<Optional<UserInfo>>

    fun clearCurrentUser(): Completable
}
