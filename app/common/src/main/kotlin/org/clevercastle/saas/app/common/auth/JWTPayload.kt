package org.clevercastle.saas.app.common.auth

import com.auth0.jwt.interfaces.DecodedJWT
import java.time.OffsetDateTime

sealed class JWTPayload


data class Auth0JWTPayload(
        val sub: String,
        val iss: String,
        val azp: String,
        val scope: String,
        val exp: OffsetDateTime,
        val iat: OffsetDateTime,
) : JWTPayload() {
    companion object {
        fun from(decodedJWT: DecodedJWT): Auth0JWTPayload {
            return Auth0JWTPayload(
                    sub = decodedJWT.subject,
                    iss = decodedJWT.issuer,
                    azp = decodedJWT.getClaim("azp").asString(),
                    scope = decodedJWT.getClaim("scope").asString(),
                    exp = decodedJWT.expiresAt.toInstant().atOffset(OffsetDateTime.now().offset),
                    iat = decodedJWT.issuedAt.toInstant().atOffset(OffsetDateTime.now().offset)
            )
        }
    }
}