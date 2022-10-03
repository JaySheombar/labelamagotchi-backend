package com.labela.labelamagotchi.labelamagotchi.controller

import com.labela.labelamagotchi.labelamagotchi.model.employee.Employee
import com.labela.labelamagotchi.labelamagotchi.service.EmployeeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/employees")
class EmployeeController(private val service: EmployeeService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }

    @GetMapping
    fun getEmployees(): Collection<Employee> = service.getEmployees()

    @GetMapping("/{id}")
    fun getEmployee(@PathVariable id: Int): Employee = service.getEmployee(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addEmployee(@RequestBody employee: Employee): Employee = service.addEmployee(employee)

    @PatchMapping
    fun updateEmployee(@RequestBody employee: Employee): Employee = service.updateEmployee(employee)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteEmployee(@PathVariable id: Int) = service.deleteEmployee(id)
}