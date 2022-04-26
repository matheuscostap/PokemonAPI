package com.costa.matheus.data

import com.costa.matheus.data.datasource.PokemonApiDataSource
import com.costa.matheus.data.model.TypeModel
import com.costa.matheus.data.model.TypeResponse
import com.costa.matheus.data.repository.TypeRepositoryImpl
import com.costa.matheus.domain.entities.Type
import com.costa.matheus.domain.repository.TypeRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class TypeRepositoryImplTest {

    @MockK
    lateinit var dataSource: PokemonApiDataSource

    lateinit var repository: TypeRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = TypeRepositoryImpl(dataSource)
    }


    @Test
    fun `getTypes should return List of Type`() = runTest {
        //Arrange
        val typeList = listOf(
            TypeModel(0, "tipo1", "tipo1url"),
            TypeModel(1, "tipo2", "tipo2url")
        )
        val datasourceReturn = TypeResponse(typeList)

        coEvery { dataSource.getTypes() } returns datasourceReturn

        //Act
        val response = repository.getTypes().await()

        //Assert
        Assert.assertEquals("response has the equal size", 2, response.size)
    }

}