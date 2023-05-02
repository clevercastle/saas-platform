package org.clevercastle.saas.app.common.internal.exception

class ErrorResponse private constructor(
    val error: String?,
    val code: String?,
    val detail: String?,
    val data: Any?
) {
    data class Builder(
        private var error: String? = null,
        private var code: String? = null,
        private var detail: String? = null,
        private var data: Any? = null
    ) {

        fun error(error: String?) = apply { this.error = error }
        fun code(code: String?) = apply { this.code = code }
        fun detail(detail: String?) = apply { this.detail = detail }
        fun data(data: Any?) = apply { this.data = data }
        fun build() = ErrorResponse(error, code, detail, data)
    }

}

