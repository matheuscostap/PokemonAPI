package com.example.matheuscosta.pokemonapi.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.matheuscosta.pokemonapi.R
import com.example.matheuscosta.pokemonapi.activity.adapters.TypeListAdapter
import com.example.matheuscosta.pokemonapi.objects.Type

import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    lateinit var gridViewTypes : GridView
    var typesArray = arrayListOf<Type>()
    lateinit var adapter : TypeListAdapter
    lateinit var timeoutLayout : ConstraintLayout
    lateinit var progressBar : ProgressBar

    val TYPE_URL : String = "https://pokeapi.co/api/v2/type/"




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //Referencias
        progressBar = findViewById(R.id.progressBar)
        timeoutLayout = findViewById(R.id.timeoutLayout)
        gridViewTypes = findViewById(R.id.gridViewTypes)
        gridViewTypes.setOnItemClickListener(this)
        adapter = TypeListAdapter(this,typesArray)
        gridViewTypes.adapter = adapter

        progressBar.visibility = View.VISIBLE

        //OkHttp
        val httpClient = OkHttpClient.Builder().connectTimeout(5,TimeUnit.SECONDS).build()
        val request = Request.Builder().url(TYPE_URL).build()

        httpClient.newCall(request).enqueue(object : Callback{

            override fun onFailure(call: Call, e: IOException) {
                Log.i("MainActivity",e.message + "TIMEOUT TIMEOUT")
                timeoutLayout.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }

            override fun onResponse(call: Call, response: Response) {
                //Extrai as informacoes relevantes do json
                val responseString = response.body()?.string() ?: "{}"

                val jsonResponse = JSONObject(responseString)
                val typesJsonArray = jsonResponse.getJSONArray("results")

                //Seta os tipos na lista
                for(i in 0..(typesJsonArray.length() - 1)){
                    val typeJsonObj = typesJsonArray.getJSONObject(i)
                    val name = typeJsonObj.getString("name")
                    val url = typeJsonObj.getString("url")
                    val typeObj = Type(name,url)
                    typesArray.add(typeObj)
                }

                //Atualiza a interface
                runOnUiThread {
                    progressBar.visibility = View.GONE
                    adapter.notifyDataSetChanged()
                }
            }

        })
    }



    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //Salva as informacoes do tipo selecionado para a proxima activity
        val editor = getSharedPreferences("type", Context.MODE_PRIVATE).edit()
        editor.putString("typeurl",typesArray.get(position).url)
        editor.putString("typename",typesArray.get(position).name)
        editor.commit()

        val intent = Intent(this, PokemonListActivity::class.java)
        //intent.putExtra("type",typesArray.get(position))
        startActivity(intent)
    }

}
