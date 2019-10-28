package com.example.matheuscosta.pokemonapi.repository

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val BASE_URL = "https://pokeapi.co/"

class PokeClient{

    companion object{
        inline fun <reified T> createClient(): T{
            val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

            val interceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                    .connectTimeout(10,TimeUnit.SECONDS)
                    .readTimeout(60,TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .build()

            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                    .create(T::class.java)
        }
    }

}