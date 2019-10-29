package com.example.matheuscosta.pokemonapi.view.mainactivity

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.matheuscosta.pokemonapi.model.AbstractModel
import com.example.matheuscosta.pokemonapi.model.type.Type
import com.example.matheuscosta.pokemonapi.repository.PokeRepository

class MainActivityViewModel (private val repository: PokeRepository): ViewModel(){

    val event = MutableLiveData<AbstractModel<List<Type>>>()

    fun getTypes(){
        repository.getTypes {types ->
            event.value = types.value
        }
    }
}