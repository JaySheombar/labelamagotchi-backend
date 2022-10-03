package com.labela.labelamagotchi.labelamagotchi.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.labela.labelamagotchi.labelamagotchi.model.employee.Employee
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
internal class EmployeeControllerTest @Autowired constructor(val mockMvc: MockMvc, val objectMapper: ObjectMapper) {
    private val baseUrl = "/api/employees"

    @Nested
    @DisplayName("GET /api/employees")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetEmployees {

        @Test
        fun `should return all employees`() {
            // when/then
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].name") { value("Agung") }
                    jsonPath("$[1].id") { value("2") }
                }
        }
    }

    @Nested
    @DisplayName("GET /api/employees/{id}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetEmployee {

        @Test
        fun `should return the employee with the given id`() {
            // given
            val employee = Employee(id = 2, name = "Alain")

            // when/then
            mockMvc.get("$baseUrl/${employee.id}")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(employee))
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
    @DisplayName("POST /api/employees")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostNewEmployee {

        @Test
        @DirtiesContext
        fun `should add new employee`() {
            // given
            val newEmployee = Employee(0, "Iris")

            // when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newEmployee)
            }

            // then
            val incrementedId = 57
            val addedEmployee = newEmployee.copy(id = incrementedId)
            performPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(addedEmployee))
                    }
                }

            mockMvc.get("$baseUrl/$incrementedId")
                .andExpect {
                    content { json(objectMapper.writeValueAsString(addedEmployee)) }
                }
        }

        @Test
        fun `should return BAD REQUEST if duplicate employee`() {
            // given
            val duplicateEmployee = Employee(id = 1, name = "Agung")

            // when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = duplicateEmployee
            }

            // then
            performPost
                .andDo { print() }
                .andExpect { status { isBadRequest() } }
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
    @DisplayName("PATCH /api/employees")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatchExistingEmployee {

        @Test
        @DirtiesContext
        fun `should update an existing employee`() {
            // given
            val updatedEmployee = Employee(id = 1, name = "updated employee name")

            // when
            val performPatch = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedEmployee)
            }

            // then
            performPatch
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(updatedEmployee))
                    }
                }

            mockMvc
                .get("$baseUrl/${updatedEmployee.id}")
                .andExpect {
                    content { json(objectMapper.writeValueAsString(updatedEmployee)) }
                }
        }

        @Test
        fun `should return BAD REQUEST if employee with given name already exists`() {
            // given
            val duplicateEmployee = Employee(id = 1, name = "Agung")

            // when
            val performPatch = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(duplicateEmployee)
            }

            // then
            performPatch
                .andDo { print() }
                .andExpect { status { isBadRequest() } }
        }

        @Test
        fun `should return NOT FOUND if no employee with given account number exists`() {
            // given
            val invalidEmployee = Employee(id = -1, name = "invalid employee")

            // when
            val performPatch = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidEmployee)
            }

            // then
            performPatch
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }

    @Nested
    @DisplayName("DELETE /api/employees/{id}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteExistingEmployee {

        @Test
        @DirtiesContext
        fun `should delete the employee with the given id`() {
            // given
            val id = 1

            // when/then
            mockMvc
                .delete("$baseUrl/$id")
                .andDo { print() }
                .andExpect { status { isNoContent() } }

            mockMvc
                .get("$baseUrl/$id")
                .andExpect { status { isNotFound() } }
        }

        @Test
        fun `should return NOT FOUND if no bank with given account name number exists`() {
            // given
            val invalidId = -1

            // when/then
            mockMvc.delete("$baseUrl/$invalidId")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }
}