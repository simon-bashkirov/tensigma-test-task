package com.testtask.domain.model.transaction

import java.util.*

data class PrevOut(

    val spent: Boolean,

    val txIndex: Int,

    val type: Int,

    val addr: String,

    val value: Int,

    val n: Int,

    val script: String,

    val additionalProperties: HashMap<String, Any>?
)
