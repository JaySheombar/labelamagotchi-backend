package com.labela.labelamagotchi.labelamagotchi.model.animal

import java.time.LocalDateTime

data class Animal(
    val id: Int? = null,
    val spawnDateTime: LocalDateTime,
    val hatchDateTime: LocalDateTime? = null,
    val deathDateTime: LocalDateTime? = null,
    val state: AnimalState = AnimalState.EGG,
    val type: AnimalType = AnimalType.TEST,
    val stage: AnimalStage = AnimalStage.EGG,
    val health: Int = 100,
    val happiness: Int = 100,
    val energy: Int = 100,
    val bladder: Int = 0,
)