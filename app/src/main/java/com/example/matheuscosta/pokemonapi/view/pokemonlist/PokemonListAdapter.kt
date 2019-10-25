package com.example.matheuscosta.pokemonapi.view.pokemonlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.matheuscosta.pokemonapi.R
import com.example.matheuscosta.pokemonapi.model.PokemonApiInfo
import com.example.matheuscosta.pokemonapi.model.Type
import com.squareup.picasso.Picasso

class PokemonListAdapter(context: Context, val objects: List<PokemonApiInfo>, val type : Type) : ArrayAdapter<PokemonApiInfo>(context, 0, objects) {

    var inflater : LayoutInflater

    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view : View
        val holder : ViewHolder

        val pokeInfo = this.objects[position]

        if (convertView == null){
            view = inflater.inflate(R.layout.pokemon_row,parent,false)
            holder = ViewHolder(view = view)
            view.tag = holder
        }else{
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        holder.tvPokeName.text = pokeInfo.name.capitalize()
        holder.tvPokeName.setBackgroundColor(type.getTypeColor(context))
        holder.tvPokeNumber.text = "#${pokeInfo.number}"
        Picasso.get().load(pokeInfo.imageURL).resize(200,200).into(holder.ivPokemon)

        return view
    }


    class ViewHolder(view: View){
        var tvPokeNumber : TextView = view.findViewById(R.id.tvPokeNumber)
        var tvPokeName : TextView = view.findViewById(R.id.tvPokeName)
        var ivPokemon : ImageView = view.findViewById(R.id.ivPokemon)
    }

}
