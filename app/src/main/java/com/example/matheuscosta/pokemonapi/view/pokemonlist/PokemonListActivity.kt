package com.example.matheuscosta.pokemonapi.view.pokemonlist

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class PokemonListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Tema e toolbar
        /*setTheme(type.getTypeTheme())
        setContentView(R.layout.activity_pokemon_list)
        toolbar.title = type.name.capitalize()
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)*/
    }


    /*private fun fillInfos(pokeInfos: List<PokemonApiInfo>){
        val listMap = pokeInfos.map { poke ->
            poke.imageURL = getImageUrl(poke.url)
            poke.number = getPokemonNumber(poke.url)
            poke
        }.toMutableList()

        pokeInfoArray.addAll(listMap)
        adapter.notifyDataSetChanged()
    }*/


    private fun getImageUrl(pokemonUrl : String): String{
        //Extrai o id do pokemon da url para fazer obter a url da imagem
        val regexp = Regex("\\/(\\d+)\\/")
        val results = regexp.find(pokemonUrl)
        val id = results?.groups?.get(1)?.value ?: 0

        var baseUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
        baseUrl+="${id}.png"

        Log.i("PokemonListActivity","URL: ${baseUrl}")
        return baseUrl
    }



    private fun getPokemonNumber(pokemonUrl: String): String{
        //Extrai o id do pokemon para utilizar como numero da pokedex
        val regexp = Regex("\\/(\\d+)\\/")
        val results = regexp.find(pokemonUrl)
        val id = results?.groups?.get(1)?.value ?: 0
        return "$id"
    }

}
