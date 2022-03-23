package com.example.matheuscosta.pokemonapi.view.mainactivity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.costa.matheus.domain.entities.Type
import com.example.matheuscosta.pokemonapi.base.NetworkState
import com.example.matheuscosta.pokemonapi.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: TypeListAdapter

    val items: MutableList<Type> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(toolbar)

        setupList()
        observeViewModel()
        viewModel.getTypes()
    }

    private fun setupList() {
        adapter = TypeListAdapter(items, {})
        binding.gridViewTypes.layoutManager = GridLayoutManager(this, 2)
        binding.gridViewTypes.adapter = adapter
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when(state) {
                    is NetworkState.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.timeoutLayout.isVisible = false
                    }

                    is NetworkState.Success -> {
                        binding.progressBar.isVisible = false
                        binding.timeoutLayout.isVisible = false

                        state.data?.let {
                            items.addAll(it)
                            adapter.notifyDataSetChanged()
                        }
                    }

                    is NetworkState.Error -> {
                        if (!state.consumed) {
                            binding.progressBar.isVisible = false
                            binding.timeoutLayout.isVisible = true
                            state.consumed = true
                            Log.i("MainActivity", "network error: ${state.throwable.message}")
                        }
                    }
                }
            }
        }
    }

}
