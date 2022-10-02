package com.labela.labelamagotchi.labelamagotchi.config

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.config.ScheduledTaskRegistrar
import org.springframework.stereotype.Component
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/***
 * Example on how to use the scheduler:
 */
@Configuration
class SchedulingConfig : SchedulingConfigurer {

    override fun configureTasks(taskRegistrar: ScheduledTaskRegistrar) {
        taskRegistrar.setScheduler(scheduler())
    }

    @Bean(destroyMethod = "shutdown")
    fun scheduler() : ExecutorService = Executors.newScheduledThreadPool(10)
}

@Component
class HeartBeat() {

//	@Scheduled(fixedRateString = "\${heartbeat.rate}")
//	fun anotherBeat() {
//		val name = Thread.currentThread().name
//		log.info("$name bonk bonk...")
//	}

    @Scheduled(cron = "* * * * * *", zone = "Europe/Amsterdam")
    fun beat() {
        val name = Thread.currentThread().name
        log.info("$name bonk bonk...")
    }

    @Scheduled(cron = "0 0 9 * * *", zone = "Europe/Amsterdam")
    fun wakeUp() {
        val name = Thread.currentThread().name
        log.info("$name Wake up")
    }

    @Scheduled(cron = "0 0 16 * * *", zone = "Europe/Amsterdam")
    fun sleep() {
        val name = Thread.currentThread().name
        log.info("$name Sleep")
    }

    companion object {
        @JvmStatic
        private val log = LoggerFactory.getLogger(HeartBeat::class.java)
    }
}