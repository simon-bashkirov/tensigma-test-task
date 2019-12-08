package com.testtask.domain.model.user

data class UserInfo(

    val session: String?,

    val account: Account,

    val profiles: List<Profile>
)
