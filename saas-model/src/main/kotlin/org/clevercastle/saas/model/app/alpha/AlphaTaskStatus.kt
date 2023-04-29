package org.clevercastle.saas.model.app.alpha

import org.clevercastle.saas.model.EnumConverter


class AlphaTaskStatusHibernateConverter : EnumConverter<AlphaTaskStatus>(AlphaTaskStatus::class.java)

enum class AlphaTaskStatus {
    Created,
    Queued,
    Assigned,
    Running,
    Success,
    Failed
}