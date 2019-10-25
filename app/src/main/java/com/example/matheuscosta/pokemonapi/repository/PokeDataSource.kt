package com.example.matheuscosta.pokemonapi.repository

import com.example.matheuscosta.pokemonapi.model.PokemonApiInfoResponse
import com.example.matheuscosta.pokemonapi.model.TypeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeDataSource{


    @GET("api/v2/type/")
    fun getTypes(): Call<TypeResponse>

    @GET("api/v2/type/{typeId}")
    fun getPokemonsByType(@Path("typeId") typeId: Int): Call<PokemonApiInfoResponse>
}