package com.testtask.domain.model.transaction

import java.util.*

class X(

    val lockTime: Int,

    val ver: Int,

    val size: Int,

    val inputs: List<Input>,

    val time: Long,

    val txIndex: Int,

    val vinSz: Int,

    val hash: String,

    val voutSz: Int,

    val relayedBy: String,

    val out: List<Out>?,

    val additionalProperties: HashMap<String, Any>?
)
