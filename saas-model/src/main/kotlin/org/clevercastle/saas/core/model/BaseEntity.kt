package org.clevercastle.saas.core.model

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import java.time.OffsetDateTime
import java.util.*
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class BaseEntity : PanacheEntityBase {
    var created_at: OffsetDateTime = OffsetDateTime.now()
    var created_by: String? = null
    var updated_at: OffsetDateTime = OffsetDateTime.now()
    var updated_by: String? = null
}

class EntityUtil {
    companion object {
        const val user = "user_"
        const val workspace = "workspace_"
        const val userWorkspaceMapping = "uwm_"
        const val workspaceUser = "wu_"

        fun genUserId(randomPart: String = UUID.randomUUID().toString()): String {
            return "${user}${randomPart}"
        }

        fun genWorkspaceId(randomPart: String = UUID.randomUUID().toString()): String {
            return "${workspace}${randomPart}"
        }

        fun genUserWorkspaceMappingId(randomPart: String = UUID.randomUUID().toString()): String {
            return "${userWorkspaceMapping}${randomPart}"
        }

        fun genWorkspaceUserId(randomPart: String = UUID.randomUUID().toString()): String {
            return "${workspaceUser}${randomPart}"
        }

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
