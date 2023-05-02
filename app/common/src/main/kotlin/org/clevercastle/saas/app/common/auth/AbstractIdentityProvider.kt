package org.clevercastle.saas.app.common.auth

import com.auth0.jwk.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.security.interfaces.RSAKey
import java.text.MessageFormat
import java.util.concurrent.TimeUnit


abstract class AbstractIdentityProvider {
    @Volatile
    private var cachedJwkProvider: JwkProvider? = null

    protected abstract fun getIssuer(): String
    protected abstract fun getJWTPayloadClass(): Class<out JWTPayload>

    protected fun verifyJWTToken(jwtToken: String): JWTPayload {
        val decodedJWT = JWT.decode(jwtToken)
        val publicKey: RSAKey = getPublicKey(decodedJWT.keyId)
            ?: throw RuntimeException(
                MessageFormat.format(
                    "Fail to get corresponding key with kid {0}",
                    decodedJWT.keyId
                )
            )
        val algorithm = Algorithm.RSA256(publicKey)
        val verifier = JWT.require(algorithm).withIssuer(getIssuer()).build()
        when (getJWTPayloadClass()) {
            Auth0JWTPayload::class.java -> {
                return Auth0JWTPayload.from(verifier.verify(jwtToken))
            }

            else -> TODO()
        }
    }

    protected open fun getPublicKey(kid: String?): RSAKey? {
        if (cachedJwkProvider == null) {
            synchronized(this) {
                if (cachedJwkProvider == null) {
                    val provider = UrlJwkProvider(getIssuer())
                    cachedJwkProvider = GuavaCachedJwkProvider(provider, 10, 1, TimeUnit.HOURS)
                }
            }
        }
        return try {
            cachedJwkProvider!![kid].publicKey as RSAKey
        } catch (e: SigningKeyNotFoundException) {
            return null
        } catch (e: JwkException) {
            return null
        }
    }
}