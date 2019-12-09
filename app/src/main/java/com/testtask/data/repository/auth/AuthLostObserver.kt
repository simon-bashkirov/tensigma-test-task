package com.testtask.data.repository.auth

import io.reactivex.Completable

interface AuthLostObserver {

    fun onAuthLost(): Completable

}