package com.labela.labelamagotchi.labelamagotchi.service

import com.labela.labelamagotchi.labelamagotchi.datasource.EmployeeDataSource
import com.labela.labelamagotchi.labelamagotchi.model.employee.Employee
import org.springframework.stereotype.Service

@Service
class EmployeeService(private val dataSource: EmployeeDataSource) {
    fun getEmployees() = dataSource.retrieveEmployees()
    fun getEmployee(id: Int) = dataSource.retrieveEmployee(id)
    fun addEmployee(employee: Employee) = dataSource.addEmployee(employee)
    fun updateEmployee(employee: Employee) = dataSource.updateEmployee(employee)
    fun deleteEmployee(id: Int) = dataSource.deleteEmployee(id)
}