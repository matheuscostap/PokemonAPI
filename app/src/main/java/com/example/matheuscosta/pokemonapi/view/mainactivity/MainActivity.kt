package com.example.matheuscosta.pokemonapi.view.mainactivity

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.view.View
import android.widget.*
import com.example.matheuscosta.pokemonapi.R
import com.example.matheuscosta.pokemonapi.model.NetworkStatus
import com.example.matheuscosta.pokemonapi.model.type.Type
import com.example.matheuscosta.pokemonapi.repository.PokeClient
import com.example.matheuscosta.pokemonapi.repository.PokeDataSource
import com.example.matheuscosta.pokemonapi.repository.PokeRepositoryImpl
import com.example.matheuscosta.pokemonapi.view.pokemonlist.PokemonListActivity

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_main.progressBar

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private var typesArray = arrayListOf<Type>()
    private lateinit var adapter : TypeListAdapter
    private val viewModel = MainActivityViewModel(PokeRepositoryImpl(PokeClient.createClient<PokeDataSource>()))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //Referencias
        gridViewTypes.setOnItemClickListener(this)
        adapter = TypeListAdapter(this, typesArray)
        gridViewTypes.adapter = adapter

        observeVM()
        viewModel.getTypes()
    }


    private fun observeVM(){
        viewModel.event.observe(this, Observer {event ->
            when(event?.status){
                NetworkStatus.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }

                NetworkStatus.SUCCESS -> {
                    progressBar.visibility = View.INVISIBLE
                    event.obj?.let { typesArray.addAll(it) }
                    adapter.notifyDataSetChanged()
                }

                NetworkStatus.ERROR -> {

                }
            }
        })
    }



    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //Salva as informacoes do tipo selecionado para a proxima activity
        val editor = getSharedPreferences("type", Context.MODE_PRIVATE).edit()
        editor.putInt("typeID",typesArray.get(position).typeId)
        editor.putString("typeurl",typesArray.get(position).url)
        editor.putString("typename",typesArray.get(position).name)
        editor.commit()

        val intent = Intent(this, PokemonListActivity::class.java)
        //intent.putExtra("type",typesArray.get(position))
        startActivity(intent)
    }

}
