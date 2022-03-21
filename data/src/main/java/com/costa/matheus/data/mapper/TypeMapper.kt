package com.costa.matheus.data.mapper

import com.costa.matheus.data.model.TypeResponse
import com.costa.matheus.domain.entities.Type

object TypeMapper {

    fun map(typeResponse: TypeResponse): List<Type> {
        return typeResponse.results.map { model ->
            Type(
                typeId = model.typeId,
                name = model.name,
                url = model.url
            )
        }
    }


}