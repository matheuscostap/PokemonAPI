package com.example.matheuscosta.pokemonapi.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.matheuscosta.pokemonapi.model.AbstractModel
import com.example.matheuscosta.pokemonapi.model.NetworkStatus
import com.example.matheuscosta.pokemonapi.model.Type
import com.example.matheuscosta.pokemonapi.model.TypeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface PokeRepository {
    fun getTypes(data: (LiveData<AbstractModel<List<Type>>>) -> Unit)
}

class PokeRepositoryImpl(private val dataSource: PokeDataSource): PokeRepository{

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
                res.value = AbstractModel(status = NetworkStatus.SUCCESS, obj = response.body()?.results)
                data(res)
            }
        })
    }

}