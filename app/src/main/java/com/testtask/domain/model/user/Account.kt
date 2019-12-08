package com.testtask.domain.model.user


data class Account(

    val accountId: String,

    val accountType: String,

    val email: String,

    val emailVerified: Boolean,

    val phone: String,

    val totpVerified: Boolean,

    val _2faMethod: String?,

    val password: String,

    val createdAt: String

)
