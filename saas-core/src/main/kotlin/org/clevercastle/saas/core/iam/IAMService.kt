package org.clevercastle.saas.core.iam

import org.clevercastle.saas.core.jwt.TokenHolder

interface IAMService {
    fun registerUser(email: String, password: String, params: Map<String, Any>): IdentityServerUser
    fun disableUser(iamId: String)
    fun enableUser(iamId: String)
    fun getUser(iamId: String): IdentityServerUser
    fun login(email: String, password: String): TokenHolder
    fun refresh(refreshToken: String): TokenHolder
}