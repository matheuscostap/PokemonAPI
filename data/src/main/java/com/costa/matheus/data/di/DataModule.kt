package com.costa.matheus.data.di

import com.costa.matheus.data.datasource.PokemonApiDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Classe responsável por fornecer uma instância do DataSource (PokemonApiDataSource)
 */

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    /**
     * "Ensinando" o Hilt como fornecer a instância do DataSource
     * Essa instância vai ser utilizada pela classe TypeRepositoryImpl
     */
    @Singleton
    @Provides
    fun providePokemonApiDataSource(): PokemonApiDataSource {
        val okHttpClient = RetrofitUtils.createOkHttpClient()
        return RetrofitUtils.createWebService(okHttpClient, "https://pokeapi.co/")
    }


}