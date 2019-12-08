package com.testtask.data.repository.user

import android.util.Log
import com.testtask.domain.model.user.UserInfo
import com.testtask.domain.repository.UserRepository
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor

class UserRepsitoryImpl(private val userDataSource: UserDataSource) : UserRepository {

    private val userPublisher = BehaviorProcessor.create<UserInfo>()

    override fun getCurrentUserInfo() = userPublisher
        .doOnSubscribe {
            userDataSource.getCurrentUser()
                .subscribe(
                    {
                        userPublisher.onNext(it)
                    },
                    {
                        Log.d("TAG", it.toString()) //TODO handle error
                    })
        } as Flowable<UserInfo>
}