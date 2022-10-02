package com.labela.labelamagotchi.labelamagotchi.datasource.mock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class MockAnimalDataSourceTest {

    private val mockAnimalDataSource = MockAnimalDataSource()

    @Test
    fun `should provide a collection of animals`() {
        // when
        val animals = mockAnimalDataSource.retrieveAnimals()

        // then
        assertThat(animals.size).isGreaterThanOrEqualTo(3)
    }

    @Test
    fun `should provide some mock animals`() {
        // when
        val animals = mockAnimalDataSource.retrieveAnimals()

        // then
        assertThat(animals).anyMatch { it.deathDateTime != null }
        assertThat(animals).anyMatch { it.hatchDateTime != null }
    }

    @Test
    fun `should be unique id`() {
        // when
        val animals = mockAnimalDataSource.retrieveAnimals()
        val numberOfIdenticalAnimals = animals
            .groupingBy { it.id }
            .eachCount()
            .filter { (_, value) -> value >= 2 }
            .size

        // then
        assertThat(numberOfIdenticalAnimals).isLessThan(1)
    }

    @Test
    fun `should provide an animal with given id`() {
        // given
        val id = 3

        // when
        val animal = mockAnimalDataSource.retrieveAnimal(id)

        // then
        assertThat(animal.id).isEqualTo(id)
    }

    @Test
    fun `should throw exception if animal does not exist`() {
        // given
        val id = -1

        // when/then
        assertThrows<NoSuchElementException> {
            mockAnimalDataSource.retrieveAnimal(id)
        }
    }
}