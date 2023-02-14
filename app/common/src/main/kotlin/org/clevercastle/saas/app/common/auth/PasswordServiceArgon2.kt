//package org.clevercastle.saas.app.common.auth
//
//import de.mkammerer.argon2.Argon2Factory
//
//class PasswordServiceArgon2 : PasswordService {
//    private val argon2 = Argon2Factory.create()
//    override fun hash(password: String): String {
//        return argon2.hash(10, 65536, 2, password.toCharArray())
//    }
//
//    override fun verify(password: String, hashedPassword: String): Boolean {
//        return argon2.verify(hashedPassword, password.toCharArray())
//    }
//}