package org.clevercastle.saas.model

import org.clevercastle.saas.base.TimeUtils
import java.time.OffsetDateTime

open class BaseModel {
    var createdAt: OffsetDateTime = TimeUtils.now()
    var createdBy: String? = null
    var updatedAt: OffsetDateTime = TimeUtils.now()
    var updatedBy: String? = null
}