package com.example.matheuscosta.pokemonapi.view.pokemonlist

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.example.matheuscosta.pokemonapi.R
import com.example.matheuscosta.pokemonapi.model.NetworkStatus
import com.example.matheuscosta.pokemonapi.model.PokemonApiInfo
import com.example.matheuscosta.pokemonapi.model.Type
import com.example.matheuscosta.pokemonapi.repository.PokeClient
import com.example.matheuscosta.pokemonapi.repository.PokeDataSource
import com.example.matheuscosta.pokemonapi.repository.PokeRepositoryImpl
import com.example.matheuscosta.pokemonapi.view.pokemondetail.PokemonDetailActivity

import kotlinx.android.synthetic.main.activity_pokemon_list.*
import kotlinx.android.synthetic.main.content_pokemon_list.*

class PokemonListActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private var pokeInfoArray = mutableListOf<PokemonApiInfo>()
    private lateinit var adapter : PokemonListAdapter
    private lateinit var type : Type
    private val viewModel = PokemonListViewModel(PokeRepositoryImpl(PokeClient.createClient<PokeDataSource>()))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Recebe as informacoes do tipo selecionado na tela anterior
        val preferences = getSharedPreferences("type", Context.MODE_PRIVATE)
        val typeID= preferences.getInt("typeID",0)
        val typeUrl = preferences.getString("typeurl","")
        val typeName = preferences.getString("typename","")

        type = Type(typeID, typeName ?: "", typeUrl ?: "")

        //Tema e toolbar
        setTheme(type.getTypeTheme())
        setContentView(R.layout.activity_pokemon_list)
        toolbar.title = type.name.capitalize()
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Adapter
        adapter = PokemonListAdapter(this, pokeInfoArray, type)
        gridViewPokemon.adapter = adapter
        gridViewPokemon.setOnItemClickListener(this)

        observeVM()
        viewModel.getPokemonsByType(type.typeId)
    }


    private fun observeVM(){
        viewModel.event.observe(this, Observer { event ->
            when(event?.status){
                NetworkStatus.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }

                NetworkStatus.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    event.obj?.let { fillInfos(it) }
                }

                NetworkStatus.ERROR -> {

                }
            }
        })
    }


    private fun fillInfos(pokeInfos: List<PokemonApiInfo>){
        val listMap = pokeInfos.map { poke ->
            poke.imageURL = getImageUrl(poke.url)
            poke.number = getPokemonNumber(poke.url)
            poke
        }.toMutableList()

        pokeInfoArray.addAll(listMap)
        adapter.notifyDataSetChanged()
    }


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



    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val intent = Intent(this, PokemonDetailActivity::class.java)
        intent.putExtra("pokeinfo",pokeInfoArray.get(position))
        intent.putExtra("type",type)
        startActivity(intent)
    }

}
