package com.costa.matheus.data

import com.costa.matheus.data.mapper.TypeMapper
import com.costa.matheus.data.model.TypeModel
import com.costa.matheus.data.model.TypeResponse
import org.junit.Assert
import org.junit.Test

class TypeMapperTest {

    @Test
    fun `TypeMapper map() should map TypeResponse to List of Type correctly`() {
        //Arrange
        val modelList = listOf(
            TypeModel(0, "tipo1", "tipo1url"),
            TypeModel(1, "tipo2", "tipo2url"),
            TypeModel(3, "tipo3", "tipo3url")
        )
        val typeResponse = TypeResponse(modelList)

        //Act
        val result = TypeMapper.map(typeResponse)

        //Assert
        Assert.assertEquals(modelList.size, result.size)

        result.forEachIndexed { index, type ->
            Assert.assertEquals(modelList[index].typeId, type.typeId)
            Assert.assertEquals(modelList[index].name, type.name)
            Assert.assertEquals(modelList[index].url, type.url)
        }
    }


}