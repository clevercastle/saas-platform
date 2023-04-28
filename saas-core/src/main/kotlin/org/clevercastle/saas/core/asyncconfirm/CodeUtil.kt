package org.clevercastle.saas.core.asyncconfirm

import java.util.*


object CodeUtil {
    private const val CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    private val RANDOM = Random()
    fun generateCode(length: Int): String {
        val code = StringBuilder()
        for (i in 0 until length) {
            val index: Int = CodeUtil.RANDOM.nextInt(CodeUtil.CHARS.length)
            code.append(CodeUtil.CHARS[index])
        }
        return code.toString()
    }
}
