package com.labela.labelamagotchi.labelamagotchi.controller

import com.labela.labelamagotchi.labelamagotchi.model.animal.Animal
import com.labela.labelamagotchi.labelamagotchi.service.AnimalService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/animals")
class AnimalController(private val service: AnimalService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }

    @GetMapping
    fun getAnimals(): Collection<Animal> = service.getAnimals()

    @GetMapping("/{id}")
    fun getAnimal(@PathVariable id: Int): Animal = service.getAnimal(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addAnimal(@RequestBody animal: Animal): Animal = service.addAnimal(animal)

    @PatchMapping
    fun updateBank(@RequestBody animal: Animal): Animal = service.updateAnimal(animal)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBank(@PathVariable id: Int) = service.deleteAnimal(id)
}