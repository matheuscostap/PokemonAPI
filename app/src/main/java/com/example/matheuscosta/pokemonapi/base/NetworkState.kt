package com.example.matheuscosta.pokemonapi.base

sealed class NetworkState<out T> {

    object Loading: NetworkState<Nothing>()

    data class Success<T>(val data: T?): NetworkState<T>()

    data class Error(val throwable: Throwable, var consumed: Boolean): NetworkState<Nothing>()

}