package com.labela.labelamagotchi.labelamagotchi.datasource.mock

import com.labela.labelamagotchi.labelamagotchi.datasource.EmployeeDataSource
import com.labela.labelamagotchi.labelamagotchi.model.employee.Employee
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository

@Repository
@Primary
class MockEmployeeDataSource : EmployeeDataSource {

    private val employees = mutableListOf(
        Employee(id = 1, name = "Agung"),
        Employee(id = 2, name = "Alain"),
        Employee(id = 3, name = "Alex"),
        Employee(id = 4, name = "Bart"),
        Employee(id = 5, name = "Bennet"),
        Employee(id = 6, name = "Cas"),
        Employee(id = 7, name = "Damian"),
        Employee(id = 8, name = "Daria"),
        Employee(id = 9, name = "Davit"),
        Employee(id = 10, name = "Donny"),
        Employee(id = 11, name = "Emils"),
        Employee(id = 12, name = "Eric"),
        Employee(id = 13, name = "Floris"),
        Employee(id = 14, name = "Freya"),
        Employee(id = 15, name = "Gavin"),
        Employee(id = 16, name = "Guy"),
        Employee(id = 17, name = "Hanzi"),
        Employee(id = 18, name = "Iraj"),
        Employee(id = 19, name = "Ivo"),
        Employee(id = 20, name = "Jeffrey"),
        Employee(id = 21, name = "Jessica"),
        Employee(id = 22, name = "Jimmie"),
        Employee(id = 23, name = "Jodie"),
        Employee(id = 24, name = "Jordain"),
        Employee(id = 25, name = "Jorn"),
        Employee(id = 26, name = "Kim"),
        Employee(id = 27, name = "Maik"),
        Employee(id = 28, name = "Maikel"),
        Employee(id = 29, name = "Marcel"),
        Employee(id = 30, name = "Mats"),
        Employee(id = 31, name = "Mert"),
        Employee(id = 32, name = "Mike"),
        Employee(id = 33, name = "Nick"),
        Employee(id = 34, name = "Remy"),
        Employee(id = 35, name = "Rick"),
        Employee(id = 36, name = "Robin"),
        Employee(id = 37, name = "Roel"),
        Employee(id = 38, name = "Rogier"),
        Employee(id = 39, name = "Roland"),
        Employee(id = 40, name = "Ronny"),
        Employee(id = 41, name = "Ruben"),
        Employee(id = 42, name = "Sander"),
        Employee(id = 43, name = "Sanne"),
        Employee(id = 44, name = "Sebastiaan"),
        Employee(id = 45, name = "Shingfei"),
        Employee(id = 46, name = "Sissy"),
        Employee(id = 47, name = "Stefan"),
        Employee(id = 48, name = "Steven"),
        Employee(id = 49, name = "Thomas"),
        Employee(id = 50, name = "Tim"),
        Employee(id = 51, name = "Timothy"),
        Employee(id = 52, name = "Tyler"),
        Employee(id = 53, name = "Vibhor"),
        Employee(id = 54, name = "Yanick"),
        Employee(id = 55, name = "Vera"),
        Employee(id = 56, name = "Yvo"),
    )

    override fun retrieveEmployees(): Collection<Employee> = employees

    override fun retrieveEmployee(id: Int): Employee = employees
        .firstOrNull { it.id == id }
        ?: throw NoSuchElementException("Could not find an employee with id: $id")

    override fun addEmployee(employee: Employee): Employee {
        if (employees contains employee) throw IllegalArgumentException("Employee already exists")

        val id = employees.maxOf { it.id }
        val newId = id + 1

        val newEmployee = employee.copy(id = newId)
        employees.add(newEmployee)

        return newEmployee
    }

    override fun updateEmployee(employee: Employee): Employee {
        if (employees contains employee) throw IllegalArgumentException("Employee already exists")

        val currentEmployee = employees
            .firstOrNull { it.id == employee.id }
            ?: throw NoSuchElementException("Could not find employee with id: ${employee.id}")

        employees.remove(currentEmployee)
        employees.add(employee)

        return employee
    }

    override fun deleteEmployee(id: Int) {
        val currentEmployee = employees
            .firstOrNull { it.id == id }
            ?: throw NoSuchElementException("Could not find employee with id: $id")

        employees.remove(currentEmployee)
    }

    private infix fun MutableList<Employee>.contains(employee: Employee): Boolean = this
        .map { it.name }
        .contains(employee.name)
}