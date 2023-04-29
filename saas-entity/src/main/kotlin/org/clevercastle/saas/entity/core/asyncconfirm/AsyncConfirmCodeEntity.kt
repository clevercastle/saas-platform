package org.clevercastle.saas.entity.core.asyncconfirm

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.clevercastle.saas.entity.BaseEntity
import org.clevercastle.saas.model.EnumConverter
import org.clevercastle.saas.model.ListConverter
import org.clevercastle.saas.model.MapConverter
import org.clevercastle.saas.model.core.asyncconfirm.AsyncConfirmCode
import java.time.OffsetDateTime
import java.util.*

@Entity(name = "async_confirm_code")
class AsyncConfirmCodeEntity : BaseEntity() {
    @Id
    val id: String = UUID.randomUUID().toString()

    //varchar(32)
    var code: String? = null

    @field:Convert(converter = AsyncConfirmCode.CodeTypeConverter::class)
    var type: AsyncConfirmCode.CodeType? = null
    var bizKey: String? = null

    @field:Convert(converter = MapConverter::class)
    var bizDetail: Map<String, String>? = null
    var description: String? = null
    var expiredAt: OffsetDateTime? = null

    @field:Convert(converter = StateConverter::class)
    var state: AsyncConfirmCode.State? = null

    @field:Convert(converter = ListConverter::class)
    var tags: List<String>? = null
    var notes: String? = null


    class StateConverter : EnumConverter<AsyncConfirmCode.State>(AsyncConfirmCode.State::class.java)

}


@ApplicationScoped
class AsyncConfirmCodeEntityRepository : PanacheRepositoryBase<AsyncConfirmCodeEntity, String> {
    fun getByCodeAndType(code: String, type: AsyncConfirmCode.CodeType): AsyncConfirmCodeEntity? {
        return find("code = ?1 and type = ?2", code, type).firstResult()
    }
}