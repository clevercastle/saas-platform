package org.clevercastle.saas.core.model.asyncconfirm

import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.clevercastle.saas.base.EnumConverter
import org.clevercastle.saas.base.MapConverter
import org.clevercastle.saas.core.model.BaseEntity
import java.time.OffsetDateTime
import java.util.*

@Entity(name = "async_confirm_code")
class AsyncConfirmCode : BaseEntity() {
    @Id
    private val id: String = UUID.randomUUID().toString()
    private val code: String? = null

    @field:Convert(converter = CodeTypeConverter::class)
    private val type: CodeType? = null
    private val bizKey: String? = null

    @field:Convert(converter = MapConverter::class)
    private val bizDetail: Map<String, String>? = null
    private val description: String? = null
    private val expiredAt: OffsetDateTime? = null
    private val state: State? = null

    //    private val tags: List<String>? = null
    private val notes: String? = null

    enum class State {
        PrePending,
        Pending,
        Replaced,
        Confirmed,
        Rejected
    }

}

enum class CodeType {
    Email
}

class CodeTypeConverter : EnumConverter<CodeType>(CodeType::class.java)