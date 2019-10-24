package com.example.matheuscosta.pokemonapi.view.pokemondetail

import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.matheuscosta.pokemonapi.R
import com.example.matheuscosta.pokemonapi.model.Move
import com.example.matheuscosta.pokemonapi.model.PokemonApiInfo
import com.example.matheuscosta.pokemonapi.model.Type
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_pokemon_detail.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import android.support.v7.widget.DividerItemDecoration
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import com.example.matheuscosta.pokemonapi.model.Pokemon
import java.util.concurrent.TimeUnit


class PokemonDetailActivity : AppCompatActivity() {

    lateinit var ivPokemon : ImageView
    lateinit var tvPokeName : TextView
    lateinit var tvPokeType1 : TextView
    lateinit var tvPokeType2 : TextView
    lateinit var tvPokeHeight : TextView
    lateinit var tvPokeWeight : TextView
    lateinit var recyclerViewSkills : RecyclerView
    lateinit var detailsBackground : ConstraintLayout
    lateinit var pokeInfo : PokemonApiInfo
    lateinit var type : Type
    lateinit var pokemon : Pokemon
    lateinit var adapter : MoveListAdapter
    lateinit var timeoutLayout : ConstraintLayout
    lateinit var progressBar : ProgressBar
    var moves = arrayListOf<Move>()
    var types = arrayListOf<Type>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Extras
        pokeInfo = intent.extras.getSerializable("pokeinfo") as PokemonApiInfo
        type = intent.extras.getSerializable("type") as Type

        //Tema e Toolbar
        setTheme(type.getTypeTheme())
        setContentView(R.layout.activity_pokemon_detail)
        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Referencias
        ivPokemon = findViewById(R.id.ivPokemon)
        tvPokeName = findViewById(R.id.tvPokeName)
        tvPokeType1 = findViewById(R.id.tvPokeType1)
        tvPokeType2 = findViewById(R.id.tvPokeType2)
        tvPokeHeight = findViewById(R.id.tvPokeHeight)
        tvPokeWeight = findViewById(R.id.tvPokeWeight)
        recyclerViewSkills = findViewById(R.id.recyclerViewSkills)
        detailsBackground = findViewById(R.id.detailsBackground)
        timeoutLayout = findViewById(R.id.timeoutLayout)
        progressBar = findViewById(R.id.progressBar)

        //Sets iniciais
        detailsBackground.setBackgroundColor(type.getTypeColor(applicationContext))
        Picasso.get().load(pokeInfo.imageURL).resize(150,150).into(ivPokemon)
        tvPokeName.text = pokeInfo.name.capitalize()

        //Lista e adapter
        adapter = MoveListAdapter(this, moves)
        recyclerViewSkills.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        recyclerViewSkills.layoutManager = layoutManager
        recyclerViewSkills.addItemDecoration(DividerItemDecoration(recyclerViewSkills.context, DividerItemDecoration.VERTICAL))

        //Progressbar
        progressBar.visibility = View.VISIBLE


        //OkHttp
        val httpClient = OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS).build()
        val request = Request.Builder().url(pokeInfo.url).build()

        httpClient.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                progressBar.visibility = View.GONE
                timeoutLayout.visibility = View.VISIBLE
            }

            override fun onResponse(call: Call, response: Response) {
                displayContent(response)
            }
        })
    }


    fun displayContent(response: Response){
        //Extrai as informacoes relevantes do json retornado
        val responseString = response.body()?.string() ?: "{}"

        val jsonResponse = JSONObject(responseString)
        val movesJsonArray = jsonResponse.getJSONArray("moves")
        val typesJsonArray = jsonResponse.getJSONArray("types")

        val height = jsonResponse.getInt("height") * 10 //decimetros para centimetros
        val weight = jsonResponse.getInt("weight") * 100 //hectograma para grama

        for(i in 0..(movesJsonArray.length() - 1)){
            val moveJsonObj = movesJsonArray.getJSONObject(i).getJSONObject("move")
            val moveName = moveJsonObj.getString("name")
            val moveUrl = moveJsonObj.getString("url")
            val moveObj = Move(moveName, moveUrl)
            moves.add(moveObj)
        }

        for(i in 0..(typesJsonArray.length() - 1)){
            val typeJsonObj = typesJsonArray.getJSONObject(i).getJSONObject("type")
            val typeName = typeJsonObj.getString("name")
            val typeUrl = typeJsonObj.getString("url")
            val typeObj = Type(typeName, typeUrl)
            types.add(typeObj)
        }

        pokemon = Pokemon(pokeInfo.name.capitalize(), height.toString(), weight.toString(), moves, types)

        //Seta as informacoes na interface
        runOnUiThread {
            progressBar.visibility = View.GONE
            tvPokeHeight.text = "$height cm"
            tvPokeWeight.text = "$weight g"

            tvPokeType1.text = types.get(0).name
            tvPokeType1.setBackgroundColor(types.get(0).getTypeColor(applicationContext))
            tvPokeType1.visibility = View.VISIBLE

            if (types.size > 1){
                tvPokeType2.visibility = View.VISIBLE
                tvPokeType2.text = types.get(1).name
                tvPokeType2.setBackgroundColor(types.get(1).getTypeColor(applicationContext))
            }
            adapter.notifyDataSetChanged()
        }
    }


    fun shareContent(){
        //Formata a string com as informacoes
        var content = pokemon.name

        content += "\nTipo:"
        for (type in types){
            content += "\n${type.name}"
        }

        content += "\nAltura: ${pokemon.height} cm"
        content += "\nPeso: ${pokemon.weight} g"
        content += "\nHabilidades:"

        for (move in moves){
            content += "\n${move.name}"
        }

        Log.i("Details",content)

        //Abre o dialog para selecionar o app
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT,content)
        intent.type = "text/plain"
        startActivity(Intent.createChooser(intent, "Compartilhar"))
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_details, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        when(id){
            R.id.action_share -> shareContent()
        }

        return super.onOptionsItemSelected(item)
    }

}
