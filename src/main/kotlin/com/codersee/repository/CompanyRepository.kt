package com.codersee.repository

import com.codersee.model.Company
import com.mongodb.client.MongoClient
import com.mongodb.client.model.Filters
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import org.bson.types.ObjectId
import jakarta.inject.Singleton

@Singleton
class CompanyRepository(
    private val mongoClient: MongoClient
) {

    fun create(company: Company): InsertOneResult =
        getCollection()
            .insertOne(company)

    fun findAll(): List<Company> =
        getCollection()
            .find()
            .toList()

    fun findById(id: String): Company? =
        getCollection()
            .find(
                Filters.eq("_id", ObjectId(id))
            )
            .toList()
            .firstOrNull()

    fun update(id: String, update: Company): UpdateResult =
        getCollection()
            .replaceOne(
                Filters.eq("_id", ObjectId(id)),
                update
            )

    fun deleteById(id: String): DeleteResult =
        getCollection()
            .deleteOne(
                Filters.eq("_id", ObjectId(id))
            )

    private fun getCollection() =
        mongoClient
            .getDatabase("example")
            .getCollection("company", Company::class.java)
}