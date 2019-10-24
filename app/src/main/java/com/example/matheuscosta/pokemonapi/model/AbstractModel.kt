package com.example.matheuscosta.pokemonapi.model

class AbstractModel<T> (val status: NetworkStatus, val obj: T? = null)