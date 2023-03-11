package org.clevercastle.saas.core.internal.auth

import java.security.Principal

data class SaasPrincipal(val userId: String, val userSub: String, val workspaceId: String?) : Principal {
    override fun getName(): String {
        return userId
    }
}