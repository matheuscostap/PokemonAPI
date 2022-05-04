package com.example.matheuscosta.pokemonapi.view.pokemondetail

import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.example.matheuscosta.pokemonapi.R

import kotlinx.android.synthetic.main.activity_pokemon_detail.*
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.matheuscosta.pokemonapi.util.RotationUtil
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import kotlinx.android.synthetic.main.content_pokemon_detail.*
import kotlinx.android.synthetic.main.content_pokemon_detail.progressBar
import kotlin.math.max
import kotlin.math.min


class PokemonDetailActivity : AppCompatActivity(), MotionLayout.TransitionListener, ScaleGestureDetector.OnScaleGestureListener {

    private val poke3DModelURL = "https://raw.githubusercontent.com/matheuscostap/GLTFModels/master/pokemons/pokeId/model.gltf"
    private var model3DOpen = false
    private val pokeNode = Node()
    private val rotateQuatY = Quaternion.axisAngle(Vector3(0f,0f,0f),0f)
    private val rotateQuatX = Quaternion.axisAngle(Vector3(0f,0f,0f),0f)
    private lateinit var gestureDetector: ScaleGestureDetector
    private var scaleFactor = 1f
    private var lastX = 0f
    private var lastY = 0f
    private var finalXAux = 0f
    private var finalYAux = 0f
    private var pinchZoom = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
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
        sceneView.setBackgroundColor(type.getTypeColor(applicationContext))
        detailsBackground.setTransitionListener(this)
        gestureDetector = ScaleGestureDetector(this,this)

        //Lista e adapter
        adapter = MoveListAdapter(this, moves)
        recyclerViewSkills.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        recyclerViewSkills.layoutManager = layoutManager
        recyclerViewSkills.addItemDecoration(DividerItemDecoration(recyclerViewSkills.context, DividerItemDecoration.VERTICAL))

        observeVM()
        viewModel.getPokemon(pokeInfo.number.toInt())

        //Click Listener é sobrescrevido pelo motion scene
        fab3D.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP){
                model3DOpen = !model3DOpen
            }
            false
        }

        sceneView.setOnTouchListener { v, event ->
            //Log.i("PokemonDetail", "Event X -> ${event.x}")
            //Log.i("PokemonDetail", "Event Y -> ${event.y}")

            gestureDetector.onTouchEvent(event)

            if (!pinchZoom){
                if(event.action == MotionEvent.ACTION_MOVE){
                    rotateModel(event.x, event.y)
                }

                if(event.action == MotionEvent.ACTION_UP){
                    /*Log.i("PokemonDetail", "***********************************************************")
                    Log.i("PokemonDetail", "RELEASE")
                    Log.i("PokemonDetail", "***********************************************************")*/
                    lastX = 0f
                    lastY = 0f
                }
            }

            false
        }**/
    }

    /*private fun getPokemon3DModel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val url = poke3DModelURL.replace("pokeId",pokeInfo.number)

            ModelRenderable.builder()
                .setSource(this, RenderableSource.Builder().setSource(
                        this,
                        Uri.parse(url),
                        RenderableSource.SourceType.GLTF2)
                        .setScale(0.3f)
                        .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                        .build())
                .setRegistryId(url)
                .build()
                .thenAccept { renderable ->
                    add3dModel(renderable)
                }
                /*.exceptionally { t ->

                }*/
        }
    }*/


    /*private fun add3dModel(renderable: ModelRenderable){
        Log.i("PokemonDetail","add3DModel()")
        pokeNode.apply {
            setParent(sceneView.scene)
            localPosition = Vector3(0f,0f,-1f)
            name = pokeInfo.name
            this.renderable = renderable
        }

        sceneView.scene.addChild(pokeNode)
    }*/


    private fun rotateModel(x: Float, y: Float){
        var dragDiffX = 0f
        var dragDiffY = 0f

        if (lastX != 0f && lastY != 0f){
            dragDiffX = x - lastX
            dragDiffY = y - lastY

            val finalX = finalXAux + dragDiffX
            val finalY = finalYAux + dragDiffY

            finalXAux = finalX
            finalYAux = finalY

            rotateQuatX.set(Vector3(1f,0f,0f),finalY)
            rotateQuatY.set(Vector3(0f,1f,0f),finalX)

            /*Log.i("PokemonDetail", "***********************************************************")
            Log.i("PokemonDetail", "dragDiffX - > $dragDiffX")
            Log.i("PokemonDetail", "dragDiffY - > $dragDiffY")
            Log.i("PokemonDetail", "finalX - > $finalX")
            Log.i("PokemonDetail", "finalY - > $finalY")
            Log.i("PokemonDetail", "***********************************************************")*/
        }

        lastX = x
        lastY = y

        val finalRotate = Quaternion.multiply(rotateQuatY, rotateQuatX)

        pokeNode.localRotation = finalRotate
    }


    private fun resizeModel(scale: Float){
        pokeNode.localScale = Vector3(scale,scale,scale)
    }


    /*
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

            getPokemon3DModel()
        }
    }*/


    /*fun shareContent(){
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

            Log.i("Details",content ?: "")

            //Abre o dialog para selecionar o app
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,content)
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Compartilhar"))
        }
    }*/


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_details, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item?.itemId

        when(id){
            //R.id.action_share -> shareContent()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
        sceneView.pause()
    }

    override fun onResume() {
        super.onResume()
        sceneView.resume()
    }

    override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
    }

    /** Motion Layout Transitions Listener **/
    override fun onTransitionChange(motionLayout: MotionLayout, startId: Int, endId: Int, progress: Float) {}

    override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
        Log.i("PokemonDetail", "onTransitionCompleted()")
        if (model3DOpen){
            Log.i("PokemonDetail", "model3DOPen == true")
            sceneView.visibility = View.VISIBLE
        }
    }

    override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
    }

    /** Scale Gesture Listener **/
    override fun onScale(detector: ScaleGestureDetector): Boolean {
        scaleFactor *= detector.scaleFactor
        scaleFactor = max(0.1f, min(scaleFactor, 3.5f))
        Log.i("PokemonDetail", "onScale -> ${scaleFactor}")
        resizeModel(scaleFactor)
        return true
    }

    override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
        pinchZoom = true
        return true
    }
    override fun onScaleEnd(detector: ScaleGestureDetector?) {
        pinchZoom = false
    }

}