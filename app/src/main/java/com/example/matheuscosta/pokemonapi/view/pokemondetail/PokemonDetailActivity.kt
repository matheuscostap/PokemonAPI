package com.example.matheuscosta.pokemonapi.view.pokemondetail

import android.arch.lifecycle.Observer
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
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_pokemon_detail.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import android.support.v7.widget.DividerItemDecoration
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import com.example.matheuscosta.pokemonapi.model.*
import com.example.matheuscosta.pokemonapi.repository.PokeClient
import com.example.matheuscosta.pokemonapi.repository.PokeRepositoryImpl
import kotlinx.android.synthetic.main.content_pokemon_detail.*
import kotlinx.android.synthetic.main.content_pokemon_detail.progressBar
import kotlinx.android.synthetic.main.content_pokemon_list.*
import java.util.concurrent.TimeUnit


class PokemonDetailActivity : AppCompatActivity() {

    private var pokemon : Pokemon? = null
    private val viewModel = PokemonDetailViewModel(PokeRepositoryImpl(PokeClient.createClient()))
    lateinit var pokeInfo : PokemonApiInfo
    lateinit var type : Type
    lateinit var adapter : MoveListAdapter
    var moves = arrayListOf<Move>()



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

        observeVM()
        viewModel.getPokemon(pokeInfo.number.toInt())
    }


    private fun observeVM(){
        viewModel.event.observe(this, Observer { event ->
            when(event?.status){
                NetworkStatus.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }

                NetworkStatus.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    event.obj?.let {
                        Log.i("PokemonDetail","Pokemon -> $it")
                        this.pokemon = it
                        showInfos()
                    }
                }

                NetworkStatus.ERROR -> {

                }
            }
        })
    }


    private fun showInfos(){
        pokemon?.let {
            tvPokeHeight.text = "${it.height * 10}cm"
            tvPokeWeight.text = "${it.weight * 100}g"

            tvPokeType1.text = it.types[0].type.name
            tvPokeType1.setBackgroundColor(it.types[0].type.getTypeColor(this))
            tvPokeType1.visibility = View.VISIBLE

            if (it.types.size > 1){
                tvPokeType2.text = it.types[1].type.name
                tvPokeType2.setBackgroundColor(it.types[1].type.getTypeColor(this))
                tvPokeType2.visibility = View.VISIBLE
            }

            val movesUnwrap = it.moves.map { move -> move.move }
            this.moves.addAll(movesUnwrap)
            adapter.notifyDataSetChanged()
        }
    }


    fun shareContent(){
        pokemon?.let {
            //Formata a string com as informacoes
            var content = pokemon?.name

            content += "\nTipo:"
            for (type in it.types){
                content += "\n${type.type.name}"
            }

            content += "\nAltura: ${it.height} cm"
            content += "\nPeso: ${it.weight} g"
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
