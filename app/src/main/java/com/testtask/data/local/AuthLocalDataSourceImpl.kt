package com.testtask.data.local

import com.auth0.android.jwt.JWT
import com.testtask.data.repository.auth.AuthLocalDataSource
import io.reactivex.Completable
import io.reactivex.processors.BehaviorProcessor
import java.util.*
import java.util.concurrent.TimeUnit

class AuthLocalDataSourceImpl : AuthLocalDataSource {

    private val tokenProcessor = BehaviorProcessor.create<Token>()

    override fun expiringSoon() =
        tokenProcessor.value?.expiresAt?.let { (it.time - System.currentTimeMillis()) in 0..ADVANCE_BEFORE_EXPIRING }
            ?: false


    override fun isExpired() =
        tokenProcessor.value?.expiresAt?.let { it.time - System.currentTimeMillis() < 0 } ?: false


    override fun getToken() = tokenProcessor.value?.string ?: ""

    override fun saveToken(newToken: String) = Completable.fromCallable {
        val jwt = JWT(newToken)
        tokenProcessor.onNext(
            Token(
                string = newToken,
                expiresAt = jwt.expiresAt,
                sessionId = jwt.getClaim("session_id").asString() ?: ""
            )
        )
    }

    override fun deleteToken() = Completable.fromCallable {
        tokenProcessor.onNext(Token("", null, ""))
    }

    override fun hasToken(): Boolean {
        return tokenProcessor.value?.isEmpty == false
    }

    override fun getSessionId() = tokenProcessor.value?.sessionId ?: ""


    private data class Token(
        val string: String,
        val expiresAt: Date?,
        val sessionId: String
    ) {
        val isEmpty
            get() = string.isEmpty()
    }

    companion object {
        private val ADVANCE_BEFORE_EXPIRING = TimeUnit.MINUTES.toMillis(1)
    }
}