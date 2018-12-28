package com.example.matheuscosta.pokemonapi.activity.adapters

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.matheuscosta.pokemonapi.R
import com.example.matheuscosta.pokemonapi.objects.Type
import kotlinx.android.synthetic.main.type_row.view.*

class TypeListAdapter(context: Context,val objects: ArrayList<Type>) : ArrayAdapter<Type>(context, 0, objects) {

    var inflater : LayoutInflater

    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view : View
        val holder : ViewHolder

        val typeObj = this.objects[position]

        if (convertView == null){
            view = inflater.inflate(R.layout.type_row,parent,false)
            holder = ViewHolder(view = view)
            view.tag = holder
        }else{
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        holder.tvType.text = typeObj.name
        holder.typeBackground.setBackgroundColor(typeObj.getTypeColor(context))

        return view
    }


    class ViewHolder(view: View){
        var tvType : TextView = view.findViewById(R.id.tvType)
        var typeBackground : ConstraintLayout = view.findViewById(R.id.typeBackground)
    }

}