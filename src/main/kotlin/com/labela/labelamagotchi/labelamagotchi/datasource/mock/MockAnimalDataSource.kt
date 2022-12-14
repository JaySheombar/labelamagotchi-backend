package com.labela.labelamagotchi.labelamagotchi.datasource.mock

import com.labela.labelamagotchi.labelamagotchi.datasource.AnimalDataSource
import com.labela.labelamagotchi.labelamagotchi.model.animal.Animal
import com.labela.labelamagotchi.labelamagotchi.model.animal.AnimalStage
import com.labela.labelamagotchi.labelamagotchi.model.animal.AnimalState
import com.labela.labelamagotchi.labelamagotchi.model.employee.Employee
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
@Primary
class MockAnimalDataSource : AnimalDataSource {

    private val spawnDateTime = LocalDateTime.of(2022, 6, 1, 0, 0, 0, 0)
    val animals = mutableListOf(
        Animal(
            id = 1,
            name = Employee(12, "Eric"),
            spawnDateTime = spawnDateTime.minusMonths(2),
            hatchDateTime = spawnDateTime.minusMonths(2).plusDays(1),
            deathDateTime = spawnDateTime.minusMonths(2).plusDays(7),
            state = AnimalState.IDLE,
            stage = AnimalStage.DECEASED,
            health = 0,
            happiness = 0,
        ),
        Animal(
            id = 2,
            name = Employee(29, "Marcel"),
            spawnDateTime = spawnDateTime.minusMonths(1),
            hatchDateTime = spawnDateTime.minusMonths(1).plusDays(1),
            deathDateTime = spawnDateTime.minusMonths(1).plusDays(7),
            state = AnimalState.IDLE,
            stage = AnimalStage.DECEASED,
            health = 0,
            happiness = 0,
        ),
        Animal(
            id = 3,
            name = Employee(32, "Mike"),
            spawnDateTime = spawnDateTime,
        ),
        Animal(
            id = 4,
            name = Employee(45, "Shingfei"),
            spawnDateTime = LocalDateTime.of(2022, 10, 1, 0, 0, 0, 0),
        ),
    )

    override fun retrieveAnimals(): Collection<Animal> = animals

    override fun retrieveAnimal(id: Int): Animal = animals
        .firstOrNull { it.id == id }
        ?: throw NoSuchElementException("Could not find an animal with id: $id")

    override fun retrieveActiveAnimal(): Animal = animals
        .filter { it.stage != AnimalStage.DECEASED }
        .maxByOrNull { it.spawnDateTime }
        ?: throw NoSuchElementException("There is no active animal")

    override fun addAnimal(animal: Animal): Animal {
        val id = animals.maxOf { it.id ?: 0 }
        val newId = id + 1

        val newAnimal = animal.copy(id = newId)
        animals.add(newAnimal)

        return newAnimal
    }

    override fun updateAnimal(animal: Animal): Animal {
        val currentAnimal = animals
            .firstOrNull { it.id == animal.id }
            ?: throw NoSuchElementException("Could not find animal with id: ${animal.id}")

        animals.remove(currentAnimal)
        animals.add(animal)

        return animal
    }

    override fun deleteAnimal(id: Int) {
        val currentAnimal = animals
            .firstOrNull { it.id == id}
            ?: throw NoSuchElementException("Could not find animal with id: $id")

        animals.remove(currentAnimal)
    }
}