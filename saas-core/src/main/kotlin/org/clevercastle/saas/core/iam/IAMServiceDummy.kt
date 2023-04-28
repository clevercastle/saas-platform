package org.clevercastle.saas.core.iam

import jakarta.enterprise.context.ApplicationScoped
import org.clevercastle.saas.core.jwt.TokenHolder

@ApplicationScoped
class IAMServiceDummy : IAMService {
    override fun registerUser(email: String, password: String, params: Map<String, Any>): IdentityServerUser {
        TODO("Not yet implemented")
    }

    override fun disableUser(iamId: String) {
        TODO("Not yet implemented")
    }

    override fun enableUser(iamId: String) {
        TODO("Not yet implemented")
    }

    override fun getUser(iamId: String): IdentityServerUser {
        TODO("Not yet implemented")
    }

    override fun login(email: String, password: String): TokenHolder {
        TODO("Not yet implemented")
    }

    override fun refresh(refreshToken: String): TokenHolder {
        TODO("Not yet implemented")
    }
}
