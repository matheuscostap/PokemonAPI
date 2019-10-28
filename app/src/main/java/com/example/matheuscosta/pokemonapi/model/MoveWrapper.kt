package com.example.matheuscosta.pokemonapi.model

import com.google.gson.annotations.Expose

class MoveWrapper (
        @Expose
        val move: Move
){
        override fun toString(): String {
                return "MoveWrapper(move=$move)"
        }
}