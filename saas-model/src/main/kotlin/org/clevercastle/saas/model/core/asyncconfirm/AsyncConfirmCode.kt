package org.clevercastle.saas.model.core.asyncconfirm

import org.clevercastle.saas.model.EnumConverter
import java.time.OffsetDateTime

class AsyncConfirmCode {
    //varchar(32)
    var code: String? = null

    var type: CodeType? = null
    var bizKey: String? = null

    var bizDetail: Map<String, String>? = null
    var description: String? = null
    var expiredAt: OffsetDateTime? = null

    var state: State? = null

    var tags: List<String>? = null
    var notes: String? = null

    enum class State {
        PrePending,
        Pending,
        Replaced,
        Confirmed,
        Rejected
    }

    enum class CodeType {
        Email
    }

    class StateConverter : EnumConverter<State>(State::class.java)
    class CodeTypeConverter : EnumConverter<CodeType>(CodeType::class.java)
}


