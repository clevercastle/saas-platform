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

class EntityPrefix {
    companion object {
        const val user = "user_"
        const val tenant = "tenant_"
        const val userTenantMapping = "utm_"
        const val tenantUser = "tu_"

        fun retrieve(id: String): String {
            val res = id.split("_")
            return when (res.size) {
                0 -> {
                    ""
                }

                1 -> {
                    res[0]
                }

                else -> {
                    res[1]
                }
            }
        }
    }
}
