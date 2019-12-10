package com.testtask.domain.model.transaction


@Suppress("unused")
data class Input(

    val sequence: Long?,

    val prevOut: PrevOut?,

    val script: String?
)
