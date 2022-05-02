package com.costa.matheus.domain

import com.costa.matheus.domain.entities.Type
import com.costa.matheus.domain.repository.TypeRepository
import com.costa.matheus.domain.usecases.GetTypesUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetTypesUseCaseTest {

    @MockK
    lateinit var repository: TypeRepository

    lateinit var useCase: GetTypesUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = GetTypesUseCase(repository)
    }

    @Test
    fun `GetTypesUseCase execute() should return List of Type`() = runTest{
        //Arrange
        val typeList = listOf(
            Type(0, "tipo1", "tipo1url"),
            Type(1, "tipo2", "tipo2url")
        )

        coEvery { repository.getTypes().await() } returns typeList

        //Act
        val response = repository.getTypes().await()

        //Assert
        Assert.assertEquals(typeList.size, response.size)
        response.forEachIndexed { index, type ->
            Assert.assertEquals(typeList[index].typeId, type.typeId)
            Assert.assertEquals(typeList[index].name, type.name)
            Assert.assertEquals(typeList[index].url, type.url)
        }

    }
}