package com.testtask.data.local

import com.auth0.android.jwt.JWT
import com.testtask.data.repository.auth.AuthLocalDataSource
import io.reactivex.Completable
import io.reactivex.processors.BehaviorProcessor
import java.util.*
import java.util.concurrent.TimeUnit

class AuthLocalDataSourceImpl(private val stringLocalStorage: LocalStorage<String>) :
    AuthLocalDataSource {

    private val tokenProcessor = BehaviorProcessor.create<Token>()

    init {
        stringLocalStorage.get(KEY_STORAGE_TOKEN)?.let { onNextToken(it) }
    }

    override fun refreshDelay() =
        tokenProcessor.value?.expiresAt?.let { it.time - System.currentTimeMillis() - ADVANCE_BEFORE_EXPIRING }
            ?: ADVANCE_BEFORE_EXPIRING


    override fun isExpired() =
        tokenProcessor.value?.expiresAt?.let { it.time - System.currentTimeMillis() < 0 } ?: false


    override fun getToken() = tokenProcessor.value?.string ?: ""

    override fun saveToken(newToken: String) = Completable.fromCallable {
        stringLocalStorage.save(KEY_STORAGE_TOKEN, newToken)
        onNextToken(newToken)
    }

    override fun deleteToken() = Completable.fromCallable {
        tokenProcessor.onNext(Token("", null, ""))
    }

    override fun hasToken(): Boolean {
        return tokenProcessor.value?.isEmpty == false
    }

    override fun getSessionId() = tokenProcessor.value?.sessionId ?: ""

    private fun onNextToken(token: String) {
        val jwt = JWT(token)
        tokenProcessor.onNext(
            Token(
                string = token,
                expiresAt = jwt.expiresAt,
                sessionId = jwt.getClaim("session_id").asString() ?: ""
            )
        )
    }

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
        private const val KEY_STORAGE_TOKEN = "storage.token"
    }
}