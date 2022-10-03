package com.labela.labelamagotchi.labelamagotchi.service

import com.labela.labelamagotchi.labelamagotchi.datasource.AnimalDataSource
import com.labela.labelamagotchi.labelamagotchi.model.animal.Animal
import org.springframework.stereotype.Service

@Service
class AnimalService(private val dataSource: AnimalDataSource) {
    fun getAnimals(): Collection<Animal> = dataSource.retrieveAnimals()
    fun getAnimal(id: Int): Animal = dataSource.retrieveAnimal(id)
    fun getActiveAnimal(): Animal = dataSource.retrieveActiveAnimal()
    fun addAnimal(animal: Animal): Animal = dataSource.addAnimal(animal)
    fun updateAnimal(animal: Animal): Animal = dataSource.updateAnimal(animal)
    fun deleteAnimal(id: Int) = dataSource.deleteAnimal(id)
}