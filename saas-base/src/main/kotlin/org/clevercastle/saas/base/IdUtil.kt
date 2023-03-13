package org.clevercastle.saas.base

import java.util.*

class IdUtil {
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
                    return "$user${randomPart}"
                }

                fun genWorkspaceId(randomPart: String = UUID.randomUUID().toString()): String {
                    return "$workspace${randomPart}"
                }

                fun genWorkspaceTeamId(randomPart: String = UUID.randomUUID().toString()): String {
                    return "$workspaceTeam${randomPart}"
                }

                fun genWorkspaceUserTeamMappingId(randomPart: String = UUID.randomUUID().toString()): String {
                    return "$userWorkspaceTeamMapping${randomPart}"
                }
            }
        }

        class Alpha {
            companion object {
                const val alphaTask = "at_"
                const val alphaTaskProject = "atp_"
                const val alphaTaskGroup = "atg_"
                fun genTaskId(randomPart: String = UUID.randomUUID().toString()): String {
                    return "$alphaTask${randomPart}"
                }

                fun genTaskProjectId(randomPart: String = UUID.randomUUID().toString()): String {
                    return "$alphaTaskProject${randomPart}"
                }

                fun genTaskGroupId(randomPart: String = UUID.randomUUID().toString()): String {
                    return "$alphaTaskGroup${randomPart}"
                }
            }


        }

    }
}