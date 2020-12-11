package com.codersee.response

import com.codersee.model.Company

class CompanyResponse(
    val id: String,
    val name: String,
    val address: String
) {
    companion object {
        fun fromEntity(company: Company): CompanyResponse =
            CompanyResponse(
                id = company.id!!.toHexString(),
                name = company.name,
                address = company.address
            )
    }
}