package com.labela.labelamagotchi.labelamagotchi.datasource

import com.labela.labelamagotchi.labelamagotchi.model.employee.Employee

interface EmployeeDataSource {
    fun retrieveEmployees(): Collection<Employee>
    fun retrieveEmployee(id: Int): Employee
    fun addEmployee(employee: Employee): Employee
    fun updateEmployee(employee: Employee): Employee
    fun deleteEmployee(id: Int)
}