package org.clevercastle.saas.app.common.auth

interface PasswordService {
    fun hash(password: String): String
    fun verify(password: String, encryptedPassword: String): Boolean
}


