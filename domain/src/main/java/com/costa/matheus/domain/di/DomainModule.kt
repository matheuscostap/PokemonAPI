package com.costa.matheus.domain.di

import com.costa.matheus.domain.repository.TypeRepository
import com.costa.matheus.domain.usecases.GetTypesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Classe responsável por fornecer uma instância de UseCase
 */

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {


    @Singleton
    @Provides
    fun provideGetTypesUseCase(typeRepository: TypeRepository): GetTypesUseCase {
        return GetTypesUseCase(typeRepository)
    }


}