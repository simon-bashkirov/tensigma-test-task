package com.testtask.domain.model.transaction

import java.util.*

data class Transaction(

    val op: String?,

    val x: X?,

    val additionalProperties: HashMap<String, Any>?
)
