package com.example.matheuscosta.pokemonapi

import com.costa.matheus.domain.entities.Type
import com.costa.matheus.domain.usecases.GetTypesUseCase
import com.example.matheuscosta.pokemonapi.base.NetworkState
import com.example.matheuscosta.pokemonapi.view.mainactivity.MainActivityViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MainActivityViewModelTest {

    @get:Rule
    val rule = MainCoroutineRule()

    @MockK
    lateinit var useCase: GetTypesUseCase

    lateinit var viewModel: MainActivityViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = MainActivityViewModel(useCase)
    }

    @Test
    fun `viewmodel initial state`() {
        //Act
        val currentState = viewModel.state.value

        //Assert
        Assert.assertTrue(currentState is NetworkState.Success && currentState.data == null)
    }

    @Test
    fun `viewmodel success state test`() = rule.runTest {
        //Arrange
        val apiReturn = listOf(
            Type(0, "tipo1", "tipo1url"),
            Type(1, "tipo2", "tipo2url")
        )

        coEvery { useCase.execute().await() } returns apiReturn

        //Act
        viewModel.getTypes()
        runCurrent()
        val currentState = viewModel.state.value

        //Assert
        Assert.assertTrue(currentState is NetworkState.Success && currentState.data != null)
        Assert.assertTrue((currentState as NetworkState.Success).data == apiReturn)
    }

    @Test
    fun `viewmodel error state test`() = rule.runTest {
        //Arrange
        val error = Exception()
        coEvery { useCase.execute().await() } throws error

        //Act
        viewModel.getTypes()
        runCurrent()
        val currentState = viewModel.state.value

        //Assert
        Assert.assertTrue(currentState is NetworkState.Error)
    }

    @Test
    fun `viewmodel loading state and success state test`() = rule.runTest {
        //Arrange
        val statesHistory = mutableListOf<NetworkState<List<Type>>>()

        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.state.toList(statesHistory)
        }

        val apiReturn = listOf(
            Type(0, "tipo1", "tipo1url"),
            Type(1, "tipo2", "tipo2url")
        )

        coEvery { useCase.execute().await() } returns apiReturn

        //Act
        viewModel.getTypes()
        runCurrent()

        //Assert
        println(statesHistory)
        Assert.assertTrue(statesHistory.isNotEmpty())

        statesHistory.forEachIndexed { index, networkState ->
            when(index) {
                1 -> {
                    Assert.assertTrue(networkState is NetworkState.Loading)
                }

                2 -> {
                    Assert.assertTrue(networkState is NetworkState.Success && networkState.data != null)
                    Assert.assertTrue((networkState as NetworkState.Success).data == apiReturn)
                }
            }
        }

        job.cancel()
    }

    @Test
    fun `viewmodel loading state and error state test`() = rule.runTest {
        //Arrange
        val statesHistory = mutableListOf<NetworkState<List<Type>>>()

        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.state.toList(statesHistory)
        }

        val error = Exception()

        coEvery { useCase.execute().await() } throws error

        //Act
        viewModel.getTypes()
        runCurrent()

        //Assert
        println(statesHistory)
        Assert.assertTrue(statesHistory.isNotEmpty())

        statesHistory.forEachIndexed { index, networkState ->
            when(index) {
                1 -> {
                    Assert.assertTrue(networkState is NetworkState.Loading)
                }

                2 -> {
                    Assert.assertTrue(networkState is NetworkState.Error)
                    Assert.assertTrue((networkState as NetworkState.Error).throwable == error)
                }
            }
        }

        job.cancel()

    }
}