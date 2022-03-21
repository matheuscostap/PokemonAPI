package com.costa.matheus.data.datasource

import com.costa.matheus.data.model.TypeResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface PokemonApiDataSource {

    @GET("api/v2/type/")
    suspend fun getTypes(): TypeResponse

}