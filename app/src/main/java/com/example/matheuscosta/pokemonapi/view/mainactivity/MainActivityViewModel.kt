package com.example.matheuscosta.pokemonapi.view.mainactivity


import com.costa.matheus.domain.entities.Type
import com.costa.matheus.domain.usecases.GetTypesUseCase
import com.example.matheuscosta.pokemonapi.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val useCase: GetTypesUseCase
): BaseViewModel(){


    suspend fun getTypes(): List<Type> {
        return useCase.execute().await()
    }


}