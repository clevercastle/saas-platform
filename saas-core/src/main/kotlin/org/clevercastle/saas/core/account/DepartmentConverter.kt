package org.clevercastle.saas.core.account

import org.clevercastle.saas.entity.core.account.DepartmentEntity
import org.clevercastle.saas.model.core.account.Department
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers


@Mapper
interface DepartmentConverter {
    companion object {
        val converter: DepartmentConverter = Mappers.getMapper(DepartmentConverter::class.java)
    }

    fun fromEntity(entity: DepartmentEntity?): Department?
}