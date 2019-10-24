package com.example.matheuscosta.pokemonapi.view.pokemondetail

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.matheuscosta.pokemonapi.R
import com.example.matheuscosta.pokemonapi.model.Move

class MoveListAdapter(val context: Context, val moves : ArrayList<Move>) : RecyclerView.Adapter<MoveListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.move_row,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return moves.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val move = moves[position]
        holder.tvMove.text = move.name.capitalize()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tvMove : TextView = view.findViewById(R.id.tvMove)
    }
}