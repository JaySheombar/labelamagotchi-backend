package com.labela.labelamagotchi.labelamagotchi.datasource

import com.labela.labelamagotchi.labelamagotchi.model.animal.Animal

interface AnimalDataSource {
    fun retrieveAnimals(): Collection<Animal>
    fun retrieveAnimal(id: Int): Animal
    fun retrieveActiveAnimal(): Animal
    fun addAnimal(animal: Animal): Animal
    fun updateAnimal(animal: Animal): Animal
    fun deleteAnimal(id: Int)
}