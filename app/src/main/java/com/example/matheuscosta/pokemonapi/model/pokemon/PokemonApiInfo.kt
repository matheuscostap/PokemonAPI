package com.example.matheuscosta.pokemonapi.model.pokemon

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PokemonApiInfo(
        @Expose
        var name : String,
        @Expose
        var url : String,
        var imageURL : String,
        var number : String) : Serializable{

        override fun toString(): String {
                return "PokemonApiInfo(name='$name', url='$url', imageURL='$imageURL', number=$number)"
        }
}