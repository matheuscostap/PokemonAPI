package com.example.matheuscosta.pokemonapi.view.pokemonlist

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.matheuscosta.pokemonapi.model.AbstractModel
import com.example.matheuscosta.pokemonapi.model.PokemonApiInfo
import com.example.matheuscosta.pokemonapi.repository.PokeRepository

class PokemonListViewModel (val repository: PokeRepository): ViewModel(){

    val event = MutableLiveData<AbstractModel<List<PokemonApiInfo>>>()

    fun getPokemonsByType(typeId: Int){
        repository.getPokemonsByType(typeId){ list ->
            event.value = list.value
        }
    }
}