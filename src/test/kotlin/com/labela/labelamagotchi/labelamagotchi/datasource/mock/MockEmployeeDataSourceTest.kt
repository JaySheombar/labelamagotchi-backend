package com.labela.labelamagotchi.labelamagotchi.datasource.mock

import com.labela.labelamagotchi.labelamagotchi.model.employee.Employee
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class MockEmployeeDataSourceTest {

    private val mockEmployeeDataSource = MockEmployeeDataSource()

    @Test
    fun `should provide a collection of employees`() {
        // when
        val employees = mockEmployeeDataSource.retrieveEmployees()

        // then
        assertThat(employees.size).isGreaterThanOrEqualTo(56)
    }

    @Test
    fun `should provide some mock employees`() {
        // when
        val employees = mockEmployeeDataSource.retrieveEmployees()

        // then
        assertThat(employees).anyMatch { it.id == 30 }
        assertThat(employees).anyMatch { it.name == "Jorn" }
    }

    @Test
    fun `should be unique id`() {
        // when
        val employees = mockEmployeeDataSource.retrieveEmployees()
        val numberOfIdenticalEmployeeId = employees
            .groupingBy { it.id }
            .eachCount()
            .filter { (_, value) -> value >= 2 }
            .size

        // then
        assertThat(numberOfIdenticalEmployeeId).isLessThan(1)
    }

    @Test
    fun `should be unique name`() {
        // when
        val employees = mockEmployeeDataSource.retrieveEmployees()
        val numberOfIdenticalEmployeeNames = employees
            .groupingBy { it.name }
            .eachCount()
            .filter { (_, value) -> value >= 2 }
            .size

        // then
        assertThat(numberOfIdenticalEmployeeNames).isLessThan(1)
    }

    @Test
    fun `should throw error when adding new employee given duplicate name`() {
        // given
        val duplicateEmployee = Employee(56, "Yvo")

        // when/then
        assertThrows<IllegalArgumentException> { mockEmployeeDataSource.addEmployee(duplicateEmployee) }
    }

    @Test
    fun `should provide an employee with given id`() {
        // given
        val id = 3

        // when
        val employee = mockEmployeeDataSource.retrieveEmployee(id)

        // then
        assertThat(employee.id).isEqualTo(id)
    }

    @Test
    fun `should throw exception if employee does not exist`() {
        // given
        val id = -1

        // when/then
        assertThrows<NoSuchElementException> { mockEmployeeDataSource.retrieveEmployee(id) }
    }
}