package com.example.matheuscosta.pokemonapi.model

import com.google.gson.annotations.Expose

class PokemonApiInfoResponse (
        @Expose
        val pokemon: List<PokemonInfoWrapper>
){

        override fun toString(): String {
                return "PokemonApiInfoResponse(pokemon=$pokemon)"
        }
}