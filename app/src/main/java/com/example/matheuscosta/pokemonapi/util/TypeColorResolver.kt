package com.example.matheuscosta.pokemonapi.util

import android.content.Context
import android.os.Build
import com.costa.matheus.domain.entities.Type
import com.example.matheuscosta.pokemonapi.R

object TypeColorResolver {

    fun getTypeColor(context: Context, type: Type): Int{
        return when(type.name) {
            "normal" -> getColorResource(context, R.color.typeNormal)
            "fighting" -> getColorResource(context, R.color.typeFighting)
            "flying" -> getColorResource(context, R.color.typeFlying)
            "poison" -> getColorResource(context, R.color.typePoison)
            "ground" -> getColorResource(context, R.color.typeGround)
            "rock" -> getColorResource(context, R.color.typeRock)
            "bug" -> getColorResource(context, R.color.typeBug)
            "ghost" -> getColorResource(context, R.color.typeGhost)
            "steel" -> getColorResource(context, R.color.typeSteel)
            "fire" -> getColorResource(context, R.color.typeFire)
            "water" -> getColorResource(context, R.color.typeWater)
            "grass" -> getColorResource(context, R.color.typeGrass)
            "electric" -> getColorResource(context, R.color.typeElectric)
            "psychic" -> getColorResource(context, R.color.typePsychic)
            "ice" -> getColorResource(context, R.color.typeIce)
            "dragon" -> getColorResource(context, R.color.typeDragon)
            "dark" -> getColorResource(context, R.color.typeDark)
            "fairy" -> getColorResource(context, R.color.typeFairy)
            "shadow" -> getColorResource(context, R.color.typeFairy)
            else -> { return getColorResource(context, R.color.typeUnknown)}
        }
    }

    private fun getColorResource(context: Context, color: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.getColor(color)
        } else {
            context.resources.getColor(color)
        }
    }
}