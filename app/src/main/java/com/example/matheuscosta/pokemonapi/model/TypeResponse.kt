package com.example.matheuscosta.pokemonapi.model

import com.google.gson.annotations.Expose

class TypeResponse (
        @Expose
        val results: List<Type>
)