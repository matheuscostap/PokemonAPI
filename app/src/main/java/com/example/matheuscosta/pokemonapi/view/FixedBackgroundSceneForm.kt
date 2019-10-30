package com.example.matheuscosta.pokemonapi.view

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.google.ar.sceneform.SceneView

class FixedBackgroundSceneView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
): SceneView(context, attrs) {

    override fun setBackground(drawable: Drawable?) {
        (drawable as? ColorDrawable)?.let { colorDrawable ->
            val argb = colorDrawable.color
            val alpha = android.graphics.Color.alpha(argb)
            val red = android.graphics.Color.red(argb)
            val green = android.graphics.Color.green(argb)
            val blue = android.graphics.Color.blue(argb)
            val sceneColor = com.google.ar.sceneform.rendering.Color(
                    red / 256F,
                    green / 256F,
                    blue / 256F,
                    alpha / 256F
            )
            this.renderer?.setClearColor(sceneColor.inverseTonemap())
        }
    }
}