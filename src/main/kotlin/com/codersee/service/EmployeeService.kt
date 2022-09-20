package com.codersee.service

import com.codersee.exception.NotFoundException
import com.codersee.model.Employee
import com.codersee.repository.EmployeeRepository
import com.codersee.request.EmployeeRequest
import jakarta.inject.Singleton
import org.bson.BsonValue

@Singleton
class EmployeeService(
    private val employeeRepository: EmployeeRepository,
    private val companyService: CompanyService

) {

    fun createEmployee(request: EmployeeRequest): BsonValue? {
        val company = request.companyId?.let { companyService.findById(it) }

        val insertedEmployee = employeeRepository.create(
            Employee(
                firstName = request.firstName,
                lastName = request.lastName,
                email = request.email,
                company = company
            )
        )
        return insertedEmployee.insertedId
    }

    fun findAll(): List<Employee> =
        employeeRepository.findAll()

    fun findById(id: String): Employee =
        employeeRepository.findById(id)
            ?: throw NotFoundException("Employee with id $id not found")

    fun updateEmployee(id: String, request: EmployeeRequest): Employee {
        val foundCompany = request.companyId?.let { companyService.findById(it) }

        val updateResult = employeeRepository.update(
            id = id,
            update = Employee(
                firstName = request.firstName,
                lastName = request.lastName,
                email = request.email,
                company = foundCompany
            )
        )

        if (updateResult.modifiedCount == 0L)
            throw throw RuntimeException("Employee with id $id was not updated")

        return findById(id)
    }

    fun deleteById(id: String) {
        val deleteResult = employeeRepository.deleteById(id)

        if (deleteResult.deletedCount == 0L)
            throw throw RuntimeException("Company with id $id was not deleted")
    }
}