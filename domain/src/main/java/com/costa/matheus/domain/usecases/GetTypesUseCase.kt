package com.costa.matheus.domain.usecases

import com.costa.matheus.domain.entities.Type
import com.costa.matheus.domain.repository.TypeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTypesUseCase @Inject constructor(
    private val repository: TypeRepository
) {

    suspend fun execute() = repository.getTypes()

}