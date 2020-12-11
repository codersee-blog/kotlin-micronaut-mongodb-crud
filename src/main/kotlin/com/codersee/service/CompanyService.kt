package com.codersee.service

import com.codersee.exception.NotFoundException
import com.codersee.model.Company
import com.codersee.repository.CompanyRepository
import com.codersee.repository.EmployeeRepository
import com.codersee.request.CompanyRequest
import org.bson.BsonValue
import javax.inject.Singleton

@Singleton
class CompanyService(
    private val companyRepository: CompanyRepository,
    private val employeeRepository: EmployeeRepository
) {

    fun createCompany(request: CompanyRequest): BsonValue? {
        val insertedCompany = companyRepository.create(
            Company(
                name = request.name,
                address = request.address
            )
        )
        return insertedCompany.insertedId
    }

    fun findAll(): List<Company> {
        return companyRepository.findAll()
    }

    fun findById(id: String): Company {
        return companyRepository.findById(id)
            ?: throw NotFoundException("Company with id $id was not found")
    }

    fun updateCompany(id: String, request: CompanyRequest): Company {
        val updateResult = companyRepository.update(
            id = id,
            update = Company(name = request.name, address = request.address)
        )

        if (updateResult.modifiedCount == 0L)
            throw throw RuntimeException("Company with id $id was not updated")

        val updatedCompany = findById(id)
        updateCompanyEmployees(updatedCompany)

        return updatedCompany
    }

    fun deleteById(id: String) {
        val deleteResult = companyRepository.deleteById(id)

        if (deleteResult.deletedCount == 0L)
            throw throw RuntimeException("Company with id $id was not deleted")
    }

    private fun updateCompanyEmployees(updatedCompany: Company) {
        employeeRepository
            .findAllByCompanyId(updatedCompany.id!!.toHexString())
            .map {
                employeeRepository.update(
                    it.id!!.toHexString(),
                    it.apply { it.company = updatedCompany }
                )
            }
    }
}