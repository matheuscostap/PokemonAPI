package com.example.matheuscosta.pokemonapi.model.type

import com.google.gson.annotations.Expose

class TypeResponse (
        @Expose
        val results: List<Type>
)