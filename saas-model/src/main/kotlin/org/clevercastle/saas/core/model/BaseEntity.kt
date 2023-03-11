package org.clevercastle.saas.core.model

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import java.time.OffsetDateTime
import java.util.*
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class BaseEntity : PanacheEntityBase {
    var createdAt: OffsetDateTime = OffsetDateTime.now()
    var createdBy: String? = null
    var updatedAt: OffsetDateTime = OffsetDateTime.now()
    var updatedBy: String? = null
}

class EntityUtil {
    companion object {
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

        class Account {
            companion object {
                const val user = "user_"
                const val workspace = "workspace_"
                const val userWorkspaceMapping = "uwm_"
                const val workspaceTeam = "wteam_"
                const val userWorkspaceTeamMapping = "uwteam_"
                fun genUserId(randomPart: String = UUID.randomUUID().toString()): String {
                    return "${user}${randomPart}"
                }

                fun genWorkspaceId(randomPart: String = UUID.randomUUID().toString()): String {
                    return "${workspace}${randomPart}"
                }

                fun genWorkspaceTeamId(randomPart: String = UUID.randomUUID().toString()): String {
                    return "${workspaceTeam}${randomPart}"
                }

                fun genWorkspaceUserTeamMappingId(randomPart: String = UUID.randomUUID().toString()): String {
                    return "${userWorkspaceTeamMapping}${randomPart}"
                }
            }
        }

        class Alpha {
            companion object {
                const val alphaTask = "alpha_"
                fun genAlphaTaskId(randomPart: String = UUID.randomUUID().toString()): String {
                    return "${alphaTask}${randomPart}"
                }
            }


        }

    }
}
