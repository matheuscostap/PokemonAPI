package com.example.matheuscosta.pokemonapi.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.matheuscosta.pokemonapi.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface PokeRepository {
    fun getTypes(data: (LiveData<AbstractModel<List<Type>>>) -> Unit)
    fun getPokemonsByType(typeId: Int, data: (LiveData<AbstractModel<List<PokemonApiInfo>>>) -> Unit)
}

class PokeRepositoryImpl(private val dataSource: PokeDataSource): PokeRepository{

    override fun getPokemonsByType(typeId: Int, data: (LiveData<AbstractModel<List<PokemonApiInfo>>>) -> Unit) {
        val res = MutableLiveData<AbstractModel<List<PokemonApiInfo>>>()
        res.value = AbstractModel(status = NetworkStatus.LOADING)
        data(res)

        dataSource.getPokemonsByType(typeId).enqueue(object : Callback<PokemonApiInfoResponse>{
            override fun onFailure(call: Call<PokemonApiInfoResponse>, t: Throwable) {
                res.value = AbstractModel(status = NetworkStatus.ERROR)
                data(res)
            }

            override fun onResponse(call: Call<PokemonApiInfoResponse>, response: Response<PokemonApiInfoResponse>) {
                val pokeInfoArray = response.body()?.pokemon?.map { poke ->  poke.pokemon}
                res.value = AbstractModel(status = NetworkStatus.SUCCESS, obj = pokeInfoArray)
                data(res)
            }
        })
    }

    override fun getTypes(data: (LiveData<AbstractModel<List<Type>>>) -> Unit) {
        val res = MutableLiveData<AbstractModel<List<Type>>>()
        res.value = AbstractModel(status = NetworkStatus.LOADING)
        data(res)

        dataSource.getTypes().enqueue(object : Callback<TypeResponse>{
            override fun onFailure(call: Call<TypeResponse>, t: Throwable) {
                res.value = AbstractModel(status = NetworkStatus.ERROR)
                data(res)
            }

            override fun onResponse(call: Call<TypeResponse>, response: Response<TypeResponse>) {
                val types = response.body()?.results?.mapIndexed { index, type -> type.typeId = index+1; type}
                res.value = AbstractModel(status = NetworkStatus.SUCCESS, obj = types)
                data(res)
            }
        })
    }

}