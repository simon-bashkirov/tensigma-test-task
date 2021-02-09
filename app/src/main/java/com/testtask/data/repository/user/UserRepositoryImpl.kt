package com.testtask.data.repository.user

import android.util.Log
import com.testtask.domain.model.user.UserInfo
import com.testtask.domain.repository.UserRepository
import com.testtask.utils.lang.Optional
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor


class UserRepositoryImpl(private val userDataSource: UserDataSource) : UserRepository {

    private val userPublisher = BehaviorProcessor.create<Optional<UserInfo>>()

    override fun getCurrentUserInfo() = userPublisher
        .doOnSubscribe {
            userDataSource.getCurrentUser()
                .subscribe(
                    {
                        userPublisher.onNext(Optional(it))
                    },
                    {
                        Log.d("TAG", it.toString()) //TODO handle error
                    })
        } as Flowable<Optional<UserInfo>>

    override fun clearCurrentUser() = Completable.fromCallable {
        userPublisher.onNext(Optional())
    }
}