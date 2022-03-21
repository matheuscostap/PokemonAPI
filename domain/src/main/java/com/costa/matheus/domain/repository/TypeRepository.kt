package com.costa.matheus.domain.repository

import com.costa.matheus.domain.entities.Type
import kotlinx.coroutines.Deferred

interface TypeRepository {

    suspend fun getTypes(): Deferred<List<Type>>

}