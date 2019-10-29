package com.example.matheuscosta.pokemonapi.repository

import com.example.matheuscosta.pokemonapi.model.pokemon.Pokemon
import com.example.matheuscosta.pokemonapi.model.pokemon.PokemonApiInfoResponse
import com.example.matheuscosta.pokemonapi.model.type.TypeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeDataSource{


    @GET("api/v2/type/")
    fun getTypes(): Call<TypeResponse>

    @GET("api/v2/type/{typeId}")
    fun getPokemonsByType(@Path("typeId") typeId: Int): Call<PokemonApiInfoResponse>

    @GET("api/v2/pokemon/{pokemonId}")
    fun getPokemon(@Path("pokemonId") pokemonId: Int): Call<Pokemon>
}