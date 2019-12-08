package com.testtask.domain.model.user

import java.util.*

data class Profile(

    val profileId: String,

    val accountId: String,

    val profileType: String,

    val firstName: String,

    val lastName: String,

    val location: String,

    val gender: String?,

    val phoneCountry: String?,

    val phoneNumber: String?,

    val email: String,

    val avatarUrl: String,

    val kycVerified: Boolean,

    val langsSpokenNames: List<String>,

    val joinedAt: Date
)
