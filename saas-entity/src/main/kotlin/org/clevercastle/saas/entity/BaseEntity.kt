package org.clevercastle.saas.entity

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.MappedSuperclass
import java.time.OffsetDateTime

@MappedSuperclass
open class BaseEntity : PanacheEntityBase {
    var createdAt: OffsetDateTime = OffsetDateTime.now()
    var createdBy: String? = null
    var updatedAt: OffsetDateTime = OffsetDateTime.now()
    var updatedBy: String? = null
}

