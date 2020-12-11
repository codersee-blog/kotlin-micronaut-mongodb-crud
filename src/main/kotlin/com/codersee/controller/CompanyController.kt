package com.codersee.controller

import com.codersee.request.CompanyRequest
import com.codersee.response.CompanyResponse
import com.codersee.service.CompanyService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import java.net.URI

@Controller("/api/company")
class CompanyController(
    private val companyService: CompanyService
) {

    @Post
    fun create(@Body request: CompanyRequest): HttpResponse<Void> {
        val createdId = companyService.createCompany(request)

        return HttpResponse.created(
            URI.create(
                createdId!!.asObjectId().value.toHexString()
            )
        )
    }

    @Get
    fun findAll(): HttpResponse<List<CompanyResponse>> {
        val companies = companyService
            .findAll()
            .map { CompanyResponse.fromEntity(it) }

        return HttpResponse.ok(companies)
    }

    @Get("/{id}")
    fun findById(@PathVariable id: String): HttpResponse<CompanyResponse> {
        val company = companyService.findById(id)

        return HttpResponse.ok(
            CompanyResponse.fromEntity(company)
        )
    }

    @Put("/{id}")
    fun update(
        @PathVariable id: String,
        @Body request: CompanyRequest
    ): HttpResponse<CompanyResponse> {
        val updatedCompany = companyService.updateCompany(id, request)

        return HttpResponse.ok(
            CompanyResponse.fromEntity(updatedCompany)
        )
    }

    @Delete("/{id}")
    fun deleteById(@PathVariable id: String): HttpResponse<Void> {
        companyService.deleteById(id)

        return HttpResponse.noContent()
    }
}