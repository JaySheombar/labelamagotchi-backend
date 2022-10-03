package com.labela.labelamagotchi.labelamagotchi.service

import com.labela.labelamagotchi.labelamagotchi.datasource.AnimalDataSource
import com.labela.labelamagotchi.labelamagotchi.model.animal.Animal
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class AnimalServiceTest {

    private val dataSource: AnimalDataSource = mockk(relaxed = true)
    private val animalService: AnimalService = AnimalService(dataSource)

    @Test
    fun `should call its data source to retrieve animals`() {
        // when
        animalService.getAnimals()

        // then
        verify(exactly = 1) { dataSource.retrieveAnimals() }
    }

    @Test
    fun `should call its data source to retrieve animal with id`() {
        // given
        val id = 3

        // when
        animalService.getAnimal(id)

        // then
        verify(exactly = 1) { dataSource.retrieveAnimal(id) }
    }

    @Test
    fun `should call its data source to retrieve current active animal`() {
        // when
        animalService.getActiveAnimal()

        // then
        verify(exactly = 1) { dataSource.retrieveActiveAnimal() }
    }

    @Test
    fun `should call its data source to add animal`() {
        // given
        val animal = Animal(spawnDateTime = LocalDateTime.now())

        // when
        animalService.addAnimal(animal)

        // then
        verify(exactly = 1) { dataSource.addAnimal(animal) }
    }

    @Test
    fun `should call its data source to update animal`() {
        // given
        val animal = Animal(spawnDateTime = LocalDateTime.now())

        // when
        animalService.updateAnimal(animal)

        // then
        verify(exactly = 1) { dataSource.updateAnimal(animal) }
    }

    @Test
    fun `should call its data source to delete animal`() {
        // given
        val id = 3

        // when
        animalService.deleteAnimal(id)

        // then
        verify(exactly = 1) { dataSource.deleteAnimal(id) }
    }
}