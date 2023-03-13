package org.clevercastle.saas.core.model.alpha

import org.clevercastle.saas.core.model.account.EnumConverter

class AlphaTaskStatusHibernateConverter : EnumConverter<AlphaTaskStatus>(AlphaTaskStatus::class.java)

enum class AlphaTaskStatus {
    Created,
    Queued,
    Assigned,
    Running,
    Success,
    Failed
}