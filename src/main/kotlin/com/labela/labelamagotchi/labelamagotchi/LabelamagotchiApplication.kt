package com.labela.labelamagotchi.labelamagotchi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class LabelamagotchiApplication

fun main(args: Array<String>) {
	runApplication<LabelamagotchiApplication>(*args)
}