package com.example.matheuscosta.pokemonapi.model

import android.content.Context
import com.example.matheuscosta.pokemonapi.R
import java.io.Serializable

class Type (var name : String = "", var url : String = "") : Serializable{

    fun getTypeColor(context : Context): Int{
        when(this.name){
            "normal" -> return context.getColor(R.color.typeNormal)
            "fighting" -> return context.getColor(R.color.typeFighting)
            "flying" -> return context.getColor(R.color.typeFlying)
            "poison" -> return context.getColor(R.color.typePoison)
            "ground" -> return context.getColor(R.color.typeGround)
            "rock" -> return context.getColor(R.color.typeRock)
            "bug" -> return context.getColor(R.color.typeBug)
            "ghost" -> return context.getColor(R.color.typeGhost)
            "steel" -> return context.getColor(R.color.typeSteel)
            "fire" -> return context.getColor(R.color.typeFire)
            "water" -> return context.getColor(R.color.typeWater)
            "grass" -> return context.getColor(R.color.typeGrass)
            "electric" -> return context.getColor(R.color.typeElectric)
            "psychic" -> return context.getColor(R.color.typePsychic)
            "ice" -> return context.getColor(R.color.typeIce)
            "dragon" -> return context.getColor(R.color.typeDragon)
            "dark" -> return context.getColor(R.color.typeDark)
            "fairy" -> return context.getColor(R.color.typeFairy)
            "shadow" -> return context.getColor(R.color.typeFairy)
            else -> { return context.getColor(R.color.typeUnknown)}
        }
    }


    fun getTypeTheme(): Int{
        when(this.name){
            "normal" -> return R.style.NormalTheme
            "fighting" -> return R.style.FightingTheme
            "flying" -> return R.style.FlyingTheme
            "poison" -> return R.style.PoisonTheme
            "ground" -> return R.style.GroundTheme
            "rock" -> return R.style.RockTheme
            "bug" -> return R.style.BugTheme
            "ghost" -> return R.style.GhostTheme
            "steel" -> return R.style.SteelTheme
            "fire" -> return R.style.FireTheme
            "water" -> return R.style.WaterTheme
            "grass" -> return R.style.GrassTheme
            "electric" -> return R.style.ElectricTheme
            "psychic" -> return R.style.PsychicTheme
            "ice" -> return R.style.IceTheme
            "dragon" -> return R.style.DragonTheme
            "dark" -> return R.style.DarkTheme
            "fairy" -> return R.style.FairyTheme
            "shadow" -> return R.style.ShadowTheme
            else -> { return R.style.UnknownTheme}
        }
    }
}