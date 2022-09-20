package com.codersee

import com.codersee.request.CompanyRequest
import com.codersee.response.CompanyResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus.*
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@MicronautTest
class CompanyControllerTest {

    @Test
    fun companyEndpoint(companyClient: CompanyClient) {

        var companies = companyClient.findAll()
        assertTrue(companies.isEmpty())

        var response = companyClient.create("Company 1", "Address 1")
        assertEquals(CREATED, response.status)
        assertNotNull(CREATED, response.header("location"))
        assertEquals(24, response.header("location")?.length) // DB _id, string of 24 hexadecimal characters

        companies = companyClient.findAll()
        assertFalse(companies.isEmpty())
        assertEquals("Company 1", companies[0].name)
        assertEquals("Address 1", companies[0].address)

        response = companyClient.create("Company 2", "Address 2")
        assertEquals(CREATED, response.status())
        assertNotNull(CREATED, response.header("location"))
        assertEquals(24, response.header("location")?.length) // DB _id, string of 24 hexadecimal characters

        companies = companyClient.findAll()
        assertEquals(2, companies.size)
        assertTrue(companies.any { companyResponse -> companyResponse.name == "Company 2" })

        val companyId = companies[0].id
        val otherCompanyId = companies[1].id
        response = companyClient.deleteById(companyId)
        assertEquals(NO_CONTENT, response.status)
        companies = companyClient.findAll()
        assertEquals(1, companies.size)

        var companyResponse: HttpResponse<CompanyResponse> =
            companyClient.update(otherCompanyId, CompanyRequest("Updated Company 2", "Update Address 2"))
        assertEquals(OK, companyResponse.status)
        assertEquals("Updated Company 2", companyResponse.body.get().name)

        companyResponse = companyClient.findById(otherCompanyId)
        assertEquals(OK, companyResponse.status)
        assertEquals("Updated Company 2", companyResponse.body.get().name)

        response = companyClient.deleteById(otherCompanyId)
        assertEquals(NO_CONTENT, response.status)
        companies = companyClient.findAll()
        assertTrue(companies.isEmpty())
    }
}