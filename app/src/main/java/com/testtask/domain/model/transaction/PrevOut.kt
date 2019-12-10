package com.testtask.domain.model.transaction

import java.util.*

@Suppress("unused")
data class PrevOut(

    val spent: Boolean,

    val txIndex: Long,

    val type: Int,

    val addr: String,

    val value: Long,

    val n: Int,

    val script: String
)
