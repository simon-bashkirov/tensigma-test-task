package com.testtask.domain.interactor

interface BaseInteractor<Params, Result> {

    fun execute(params: Params): Result

}