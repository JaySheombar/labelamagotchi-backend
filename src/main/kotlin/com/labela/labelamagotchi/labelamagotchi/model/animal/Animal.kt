package com.labela.labelamagotchi.labelamagotchi.model.animal

import com.labela.labelamagotchi.labelamagotchi.model.employee.Employee
import java.time.LocalDateTime

data class Animal(
    val id: Int? = null,
    val spawnDateTime: LocalDateTime,
    val hatchDateTime: LocalDateTime? = null,
    val deathDateTime: LocalDateTime? = null,
    val name: Employee? = null,
    val state: AnimalState = AnimalState.EGG,
    val type: AnimalType = AnimalType.TEST,
    val stage: AnimalStage = AnimalStage.EGG,
    val health: Int = 100,
    val happiness: Int = 100,
    val energy: Int = 100,
    val bladder: Int = 0,
)