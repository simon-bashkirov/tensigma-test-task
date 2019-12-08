package com.testtask.domain.interactor

import io.reactivex.Completable
import io.reactivex.Flowable

interface CompletableInteractor<Params> : BaseInteractor<Params, Completable>

interface FlowableInteractor<Params, Result> : BaseInteractor<Params, Flowable<Result>>

