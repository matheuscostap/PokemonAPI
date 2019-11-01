package com.example.matheuscosta.pokemonapi.model.pokemon

import com.example.matheuscosta.pokemonapi.model.move.MoveWrapper
import com.example.matheuscosta.pokemonapi.model.type.TypeWrapper
import com.google.gson.annotations.Expose

class Pokemon(
        @Expose
        val name : String,
        @Expose
        val height : Int,
        @Expose
        var weight : Int,
        @Expose
        var moves : ArrayList<MoveWrapper>,
        @Expose
        var types : ArrayList<TypeWrapper>){

        override fun toString(): String {
            return "Pokemon(name='$name', height=$height, weight=$weight, moves=$moves, types=$types)"
        }
}