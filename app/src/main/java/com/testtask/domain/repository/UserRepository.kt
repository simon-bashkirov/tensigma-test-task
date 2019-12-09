package com.testtask.domain.repository

import com.testtask.data.util.Optional
import com.testtask.domain.model.user.UserInfo
import io.reactivex.Completable
import io.reactivex.Flowable

interface UserRepository {

    fun getCurrentUserInfo(): Flowable<Optional<UserInfo>>

    fun clearCurrentUser(): Completable
}
