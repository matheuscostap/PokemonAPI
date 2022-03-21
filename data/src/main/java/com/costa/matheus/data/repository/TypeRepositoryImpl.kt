package com.costa.matheus.data.repository

import com.costa.matheus.data.datasource.PokemonApiDataSource
import com.costa.matheus.data.mapper.TypeMapper
import com.costa.matheus.domain.entities.Type
import com.costa.matheus.domain.repository.TypeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TypeRepositoryImpl @Inject constructor(
    private val dataSource: PokemonApiDataSource
): TypeRepository {

    override suspend fun getTypes() = withContext(Dispatchers.IO) {
        async {
            TypeMapper.map(dataSource.getTypes())
        }
    }

}