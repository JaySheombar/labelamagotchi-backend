package com.labela.labelamagotchi.labelamagotchi.service

import com.labela.labelamagotchi.labelamagotchi.datasource.EmployeeDataSource
import com.labela.labelamagotchi.labelamagotchi.model.employee.Employee
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class EmployeeServiceTest {

    private val dataSource: EmployeeDataSource = mockk(relaxed = true)
    private val employeeService: EmployeeService = EmployeeService(dataSource)

    @Test
    fun `should call its datasource to retrieve employees`() {
        // when
        employeeService.getEmployees()

        // then
        verify(exactly = 1) { dataSource.retrieveEmployees() }
    }

    @Test
    fun `should call its datasource to retrieve employee given id`() {
        // given
        val id = 1

        // when
        employeeService.getEmployee(id)

        // then
        verify(exactly = 1) { dataSource.retrieveEmployee(id) }
    }

    @Test
    fun `should call its datasource to add employee given employee`() {
        // given
        val newEmployee = Employee(57, "Iris")

        // when
        employeeService.addEmployee(newEmployee)

        // then
        verify(exactly = 1) { dataSource.addEmployee(newEmployee) }
    }

    @Test
    fun `should call its datasource to update employee given employee`() {
        // given
        val employee = Employee(id = 35, name = "Rick")

        // when
        employeeService.updateEmployee(employee)

        // then
        verify(exactly = 1) { dataSource.updateEmployee(employee) }
    }

    @Test
    fun `should call its datasource to delete employee given id`() {
        // given
        val id = 1

        // when
        employeeService.deleteEmployee(id)

        // then
        verify(exactly = 1) { dataSource.deleteEmployee(id) }
    }
}