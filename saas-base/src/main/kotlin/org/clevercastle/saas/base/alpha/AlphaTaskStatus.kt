package org.clevercastle.saas.base.alpha

import org.clevercastle.saas.base.EnumConverter


class AlphaTaskStatusHibernateConverter : EnumConverter<AlphaTaskStatus>(AlphaTaskStatus::class.java)

enum class AlphaTaskStatus {
    Created,
    Queued,
    Assigned,
    Running,
    Success,
    Failed
}