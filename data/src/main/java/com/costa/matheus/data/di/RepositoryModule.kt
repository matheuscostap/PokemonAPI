package com.costa.matheus.data.di

import com.costa.matheus.data.repository.TypeRepositoryImpl
import com.costa.matheus.domain.repository.TypeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Interface responsável por prover uma implementação da classe TypeRepository
 *
 */

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    /**
     * Nesse caso, por ser uma classe normal, não precisa "ensinar" o Hilt
     * Essa instância vai ser utilizada no modulo domain, pelos UseCases
     */
    @Binds
    fun TypeRepositoryImpl.binds(): TypeRepository

}