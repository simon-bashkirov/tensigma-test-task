package com.testtask.domain.mapper

interface Mapper<Source, Destination> {

    fun map(source: Source): Destination
}