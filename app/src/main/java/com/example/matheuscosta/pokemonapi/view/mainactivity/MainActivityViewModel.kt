package com.example.matheuscosta.pokemonapi.view.mainactivity


import com.costa.matheus.domain.entities.Type
import com.costa.matheus.domain.usecases.GetTypesUseCase
import com.example.matheuscosta.pokemonapi.base.BaseViewModel
import com.example.matheuscosta.pokemonapi.base.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val useCase: GetTypesUseCase
): BaseViewModel(){

    private val privateState = MutableStateFlow<NetworkState<List<Type>>>(NetworkState.Success(null))
    val state: StateFlow<NetworkState<List<Type>>> get() = privateState


    fun getTypes() {
        jobs add launch {
            privateState.value = NetworkState.Loading
            try {
                val response = useCase.execute().await()
                privateState.value = NetworkState.Success(response)
            } catch (t: Throwable) {
                privateState.value = NetworkState.Error(t, false)
            }
        }
    }

}