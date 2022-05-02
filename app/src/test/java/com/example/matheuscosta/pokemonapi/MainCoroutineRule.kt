package com.example.matheuscosta.pokemonapi

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Classe necessária para executar os trechos de código das coroutines que usam
 * Dispatchers.main, ou seja, que rodam na main thread
 */
@ExperimentalCoroutinesApi
class MainCoroutineRule: TestWatcher() {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)


    override fun starting(description: Description?) {
        super.starting(description)

        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)

        Dispatchers.resetMain()
    }

    fun runTest(function: suspend TestScope.() -> Unit) = testScope.runTest { function() }
}