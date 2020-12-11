package com.codersee.controller

import com.codersee.request.EmployeeRequest
import com.codersee.response.EmployeeResponse
import com.codersee.service.EmployeeService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import java.net.URI

@Controller("/api/employee")
class EmployeeController(
    private val employeeService: EmployeeService
) {

    @Post
    fun create(@Body request: EmployeeRequest): HttpResponse<Void> {
        val createdId = employeeService.createEmployee(request)

        return HttpResponse.created(
            URI.create(
                createdId!!.asObjectId().value.toHexString()
            )
        )
    }

    @Get
    fun findAll(): HttpResponse<List<EmployeeResponse>> {
        val employees = employeeService
            .findAll()
            .map { EmployeeResponse.fromEntity(it) }

        return HttpResponse.ok(employees)
    }

    @Get("/{id}")
    fun findById(@PathVariable id: String): HttpResponse<EmployeeResponse> {
        val employee = employeeService.findById(id)

        return HttpResponse.ok(
            EmployeeResponse.fromEntity(employee)
        )
    }

    @Put("/{id}")
    fun update(
        @PathVariable id: String,
        @Body request: EmployeeRequest
    ): HttpResponse<EmployeeResponse> {
        val updatedEmployee = employeeService.updateEmployee(id, request)

        return HttpResponse.ok(
            EmployeeResponse.fromEntity(updatedEmployee)
        )
    }

    @Delete("/{id}")
    fun deleteById(@PathVariable id: String): HttpResponse<Void> {
        employeeService.deleteById(id)

        return HttpResponse.noContent()
    }
}