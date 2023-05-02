package org.clevercastle.saas.core.jwt

import java.time.OffsetDateTime

data class TokenHolder(
    val accessToken: String,
    val idToken: String,
    val refreshToken: String?,
    val tokenType: String,
    val expiresAt: OffsetDateTime,
    val scope: String
)