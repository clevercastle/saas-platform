package org.clevercastle.saas.app.common.auth

import java.security.Principal

class SaasPrincipal(val userId: String, val userSub: String) : Principal {

    override fun getName(): String {
        return userId
    }
}