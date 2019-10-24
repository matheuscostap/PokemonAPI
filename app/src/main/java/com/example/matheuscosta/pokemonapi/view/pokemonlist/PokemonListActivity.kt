package com.example.matheuscosta.pokemonapi.view.pokemonlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.ProgressBar
import com.example.matheuscosta.pokemonapi.R
import com.example.matheuscosta.pokemonapi.model.PokemonApiInfo
import com.example.matheuscosta.pokemonapi.model.Type
import com.example.matheuscosta.pokemonapi.view.pokemondetail.PokemonDetailActivity

import kotlinx.android.synthetic.main.activity_pokemon_list.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

class PokemonListActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    lateinit var gridViewPokemon : GridView
    lateinit var timeoutLayout : ConstraintLayout
    var pokeInfoArray = arrayListOf<PokemonApiInfo>()
    lateinit var adapter : PokemonListAdapter
    lateinit var type : Type
    lateinit var progressBar : ProgressBar



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Recebe as informacoes do tipo selecionado na tela anterior
        val preferences = getSharedPreferences("type", Context.MODE_PRIVATE)
        val typeUrl = preferences.getString("typeurl","")
        val typeName = preferences.getString("typename","")

        type = Type(typeName,typeUrl)

        //Tema e toolbar
        setTheme(type.getTypeTheme())
        setContentView(R.layout.activity_pokemon_list)
        toolbar.title = type.name.capitalize()
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Referencias
        progressBar = findViewById(R.id.progressBar)
        timeoutLayout = findViewById(R.id.timeoutLayout)
        gridViewPokemon = findViewById(R.id.gridViewPokemon)
        gridViewPokemon.setOnItemClickListener(this)

        //Adapter
        adapter = PokemonListAdapter(this, pokeInfoArray, type)
        gridViewPokemon.adapter = adapter

        progressBar.visibility = View.VISIBLE

        //OkHttp
        val httpClient = OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS).build()
        val request = Request.Builder().url(type.url).build()

        httpClient.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                progressBar.visibility = View.GONE
                timeoutLayout.visibility = View.VISIBLE
            }

            override fun onResponse(call: Call, response: Response) {
                //Extrai as informacoes relevantes do json
                val responseString = response.body()?.string() ?: "{}"

                val jsonResponse = JSONObject(responseString)
                val typesJsonArray = jsonResponse.getJSONArray("pokemon")

                //Seta as informacoes na lista
                for(i in 0..(typesJsonArray.length() - 1)){
                    val arrayObj = typesJsonArray.getJSONObject(i)
                    val pokemonObj = arrayObj.getJSONObject("pokemon")
                    val name = pokemonObj.getString("name")
                    val url = pokemonObj.getString("url")
                    val imageURL = getImageUrl(url)
                    val number = getPokemonNumber(url)
                    val pokeInfoObj = PokemonApiInfo(name,url,imageURL,number)
                    pokeInfoArray.add(pokeInfoObj)
                }

                //Atualiza a interface
                runOnUiThread {
                    progressBar.visibility = View.GONE
                    adapter.notifyDataSetChanged()
                }
            }
        })

    }



    fun getImageUrl(pokemonUrl : String): String{
        //Extrai o id do pokemon da url para fazer obter a url da imagem
        val regexp = Regex("\\/(\\d+)\\/")
        val results = regexp.find(pokemonUrl)
        val id = results?.groups?.get(1)?.value ?: 0

        var baseUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
        baseUrl+="${id}.png"

        Log.i("PokemonListActivity","URL: ${baseUrl}")
        return baseUrl
    }



    fun getPokemonNumber(pokemonUrl: String): String{
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
