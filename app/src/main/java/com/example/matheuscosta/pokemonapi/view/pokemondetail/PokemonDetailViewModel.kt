package com.example.matheuscosta.pokemonapi.view.pokemondetail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.matheuscosta.pokemonapi.model.AbstractModel
import com.example.matheuscosta.pokemonapi.model.Pokemon
import com.example.matheuscosta.pokemonapi.repository.PokeRepository

class PokemonDetailViewModel (private val repository: PokeRepository): ViewModel(){

    val event = MutableLiveData<AbstractModel<Pokemon>>()

    fun getPokemon(pokemonId: Int){
        repository.getPokemon(pokemonId){ poke ->
            event.value = poke.value
        }
    }

}