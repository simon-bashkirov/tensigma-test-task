package com.testtask.domain.mapper

interface Mapper<Source, Destination> {

    fun mapTo(source: Source): Destination
}