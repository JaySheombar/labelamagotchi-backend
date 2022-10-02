package com.labela.labelamagotchi.labelamagotchi.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.labela.labelamagotchi.labelamagotchi.model.animal.Animal
import com.labela.labelamagotchi.labelamagotchi.model.animal.AnimalStage
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.*
import java.time.LocalDateTime

@SpringBootTest
@AutoConfigureMockMvc
internal class AnimalControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper,
) {
    private val baseUrl = "/api/animals"

    private val spawnDateTime = LocalDateTime.of(2022, 10, 1, 0, 0, 0, 0)

    @Nested
    @DisplayName("GET /api/animals")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetAnimals {

        @Test
        fun `should return all animals`() {
            // when/then
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].stage") { value("${AnimalStage.DECEASED}") }
                    jsonPath("$[1].id") { value("2") }
                }
        }
    }

    @Nested
    @DisplayName("GET /api/animals/{id}")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetAnimal {

        @Test
        fun `should return the animal with the given id`() {
            // given
            val animal = Animal(id = 3, spawnDateTime = spawnDateTime)

            // when/then
            mockMvc.get("$baseUrl/${animal.id}")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(animal))
                    }
                }
        }

        @Test
        fun `should return NOT FOUND if the id does not exist`() {
            // given
            val id = -1

            // when
            mockMvc.get("$baseUrl/$id")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }

    @Nested
    @DisplayName("POST /api/animals")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class PostNewAnimal {

        @Test
        @DirtiesContext
        fun `should add new animal`() {
            // given
            val newAnimal = Animal(spawnDateTime = spawnDateTime)

            // when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newAnimal)
            }

            // then
            val addedAnimal = newAnimal.copy(id = 4)
            performPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(addedAnimal))
                    }
                }

            mockMvc.get("$baseUrl/4")
                .andExpect {
                    content { json(objectMapper.writeValueAsString(addedAnimal)) }
                }
        }

        @Test
        fun `should return BAD REQUEST if invalid data`() {
            // given
            val invalidData = "invalid"

            // when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = invalidData
            }

            // then
            performPost
                .andDo { print() }
                .andExpect { status { isBadRequest() } }
        }
    }

    @Nested
    @DisplayName("PATCH /api/animals")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class PatchExistingAnimal {

        @Test
        @DirtiesContext
        fun `should update an existing animal`() {
            // given
            val updatedAnimal = Animal(
                id = 3,
                spawnDateTime = spawnDateTime,
                stage = AnimalStage.DECEASED,
            )

            // when
            val performPatch = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedAnimal)
            }

            // then
            performPatch
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(updatedAnimal))
                    }
                }

            mockMvc
                .get("$baseUrl/${updatedAnimal.id}")
                .andExpect {
                    content { json(objectMapper.writeValueAsString(updatedAnimal)) }
                }
        }

        @Test
        fun `should return BAD REQUEST if no bank with given account number exists`() {
            // given
            val invalidAnimal = Animal(id = -1, spawnDateTime = spawnDateTime)

            // when
            val performPatch = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidAnimal)
            }

            // then
            performPatch
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }

    @Nested
    @DisplayName("DELETE /api/animals/{id}")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class DeleteExistingAnimal {

        @Test
        @DirtiesContext
        fun `should delete the animal with the given id`() {
            // given
            val accountNumber = 1

            // when/then
            mockMvc
                .delete("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect { status { isNoContent() } }

            mockMvc
                .get("$baseUrl/$accountNumber")
                .andExpect { status { isNotFound() } }
        }

        @Test
        fun `should return NOT FOUND if no bank with given account name number exists`() {
            // given
            val invalidAccountNumber = -1

            // when/then
            mockMvc.delete("$baseUrl/$invalidAccountNumber")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }
}