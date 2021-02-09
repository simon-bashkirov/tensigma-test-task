package com.testtask.domain.interactor

interface BaseInteractor<Params, Result> {

    operator fun invoke(params: Params): Result

}