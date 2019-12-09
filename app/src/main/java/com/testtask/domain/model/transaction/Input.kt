package com.testtask.domain.model.transaction

import java.util.*

data class Input(

    val sequence: Int?,

    val prevOut: PrevOut?,

    val script: String?,

    val additionalProperties: HashMap<String, Any>?
)
