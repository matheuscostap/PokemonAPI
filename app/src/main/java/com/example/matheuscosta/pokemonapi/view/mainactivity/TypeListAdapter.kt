package com.example.matheuscosta.pokemonapi.view.mainactivity

import android.view.LayoutInflater
import com.example.matheuscosta.pokemonapi.databinding.TypeRowBinding
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.costa.matheus.domain.entities.Type
import com.example.matheuscosta.pokemonapi.util.TypeColorResolver

class TypeListAdapter(
    private val items: List<Type>,
    private val onItemClick: (Type) -> Unit
): RecyclerView.Adapter<TypeListAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: TypeRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TypeRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val type = items[position]
        holder.binding.tvType.text = type.name
        holder.binding.typeBackground.setBackgroundColor(
            TypeColorResolver.getTypeColor(holder.itemView.context, type)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

}