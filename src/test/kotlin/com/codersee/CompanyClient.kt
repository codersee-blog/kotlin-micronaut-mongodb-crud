package com.codersee

import com.codersee.request.CompanyRequest
import com.codersee.response.CompanyResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.http.client.annotation.Client

@Client("/api/company")
interface CompanyClient {

    @Post
    fun create(name: String, address: String): HttpResponse<Void>

    @Get
    fun findAll(): List<CompanyResponse>

    @Get("/{id}")
    fun findById(@RequestAttribute id: String): HttpResponse<CompanyResponse>

    @Put("/{id}")
    fun update(
        @RequestAttribute id: String,
        @Body request: CompanyRequest
    ) : HttpResponse<CompanyResponse>

    @Delete("/{id}")
    fun deleteById(@RequestAttribute id: String): HttpResponse<Void>
}