package com.example.matheuscosta.pokemonapi.repository

import com.example.matheuscosta.pokemonapi.model.TypeResponse
import retrofit2.Call
import retrofit2.http.GET

interface PokeDataSource{


    @GET("api/v2/type/")
    fun getTypes(): Call<TypeResponse>


}