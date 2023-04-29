package org.clevercastle.saas.model.app

import org.clevercastle.saas.model.EnumConverter


enum class ResourceOwnerType {
    Team,
    User
}

class ResourceOwnerTypeConverter : EnumConverter<ResourceOwnerType>(ResourceOwnerType::class.java)
