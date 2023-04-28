package org.clevercastle.saas.core.model.asyncconfirm

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.clevercastle.saas.base.EnumConverter
import org.clevercastle.saas.base.ListConverter
import org.clevercastle.saas.base.MapConverter
import org.clevercastle.saas.core.model.BaseEntity
import java.time.OffsetDateTime
import java.util.*

@Entity(name = "async_confirm_code")
class AsyncConfirmCodeEntity : BaseEntity() {
    @Id
    val id: String = UUID.randomUUID().toString()

    //varchar(32)
    var code: String? = null

    @field:Convert(converter = CodeTypeConverter::class)
    var type: CodeType? = null
    var bizKey: String? = null

    @field:Convert(converter = MapConverter::class)
    var bizDetail: Map<String, String>? = null
    var description: String? = null
    var expiredAt: OffsetDateTime? = null

    @field:Convert(converter = StateConverter::class)
    var state: State? = null

    @field:Convert(converter = ListConverter::class)
    var tags: List<String>? = null
    var notes: String? = null

    enum class State {
        PrePending,
        Pending,
        Replaced,
        Confirmed,
        Rejected
    }

    class StateConverter : EnumConverter<State>(State::class.java)

}

enum class CodeType {
    Email
}

class CodeTypeConverter : EnumConverter<CodeType>(CodeType::class.java)


@ApplicationScoped
class AsyncConfirmCodeEntityRepository : PanacheRepositoryBase<AsyncConfirmCodeEntity, String> {
    fun getByCodeAndType(code: String, type: CodeType): AsyncConfirmCodeEntity? {
        return find("code = ?1 and type = ?2", code, type).firstResult()
    }
}