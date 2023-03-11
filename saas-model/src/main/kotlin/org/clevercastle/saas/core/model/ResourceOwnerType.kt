package org.clevercastle.saas.core.model

import javax.persistence.AttributeConverter

enum class ResourceOwnerType {
    Team,
    User
}

class ResourceOwnerTypeConverter : AttributeConverter<ResourceOwnerType, String> {
    override fun convertToDatabaseColumn(attribute: ResourceOwnerType?): String {
        return attribute!!.name
    }

    override fun convertToEntityAttribute(dbData: String?): ResourceOwnerType {
        return ResourceOwnerType.valueOf(dbData!!)
    }
}