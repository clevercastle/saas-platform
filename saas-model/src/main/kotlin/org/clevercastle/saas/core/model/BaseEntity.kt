package org.clevercastle.saas.core.model

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import java.time.OffsetDateTime
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class BaseEntity : PanacheEntityBase {
    var createdAt: OffsetDateTime = OffsetDateTime.now()
    var createdBy: String? = null
    var updatedAt: OffsetDateTime = OffsetDateTime.now()
    var updatedBy: String? = null
}

