package org.clevercastle.saas.core.model

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import java.time.OffsetDateTime
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class BaseEntity : PanacheEntityBase {
    var created_at: OffsetDateTime = OffsetDateTime.now()
    var created_by: String? = null
    var updated_at: OffsetDateTime = OffsetDateTime.now()
    var updated_by: String? = null
}