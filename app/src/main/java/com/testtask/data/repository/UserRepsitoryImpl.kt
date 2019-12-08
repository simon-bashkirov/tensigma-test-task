package com.testtask.data.repository

import com.testtask.domain.model.user.Account
import com.testtask.domain.model.user.Profile
import com.testtask.domain.model.user.UserInfo
import com.testtask.domain.repository.UserRepository
import io.reactivex.Flowable
import java.util.*

class UserRepsitoryImpl : UserRepository {
    override fun getCurrentUserInfo(): Flowable<UserInfo> {
        //TODO mock data
        return Flowable.just(
            UserInfo(
                session = null,
                account = Account(
                    accountId = "mockAccountId",

                    accountType = "mockAccountType",

                    email = "mock@email.com",

                    emailVerified = false,

                    phone = "mockPhone",

                    totpVerified = false,

                    _2faMethod = null,

                    password = "mock",

                    createdAt = "mock"
                ),
                profiles = listOf(
                    Profile(
                        profileId = "mock",

                        accountId = "mock",

                        profileType = "mock",

                        firstName = "mock",

                        lastName = "mock",

                        location = "mock",

                        gender = null,

                        phoneCountry = null,

                        phoneNumber = null,

                        email = "mock@email.com",

                        avatarUrl = "",

                        kycVerified = false,

                        langsSpokenNames = emptyList(),

                        joinedAt = Date()
                    )
                )
            )
        )
    }
}