package com.codersee.repository

import com.codersee.model.Employee
import com.mongodb.client.MongoClient
import com.mongodb.client.model.Filters
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import org.bson.types.ObjectId
import javax.inject.Singleton

@Singleton
class EmployeeRepository(
    private val mongoClient: MongoClient
) {

    fun create(employee: Employee): InsertOneResult =
        getCollection()
            .insertOne(employee)

    fun findAll(): List<Employee> =
        getCollection()
            .find()
            .toList()

    fun findAllByCompanyId(companyId: String): List<Employee> =
        getCollection()
            .find(
                Filters.eq("company._id", ObjectId(companyId))
            )
            .toList()


    fun findById(id: String): Employee? =
        getCollection()
            .find(
                Filters.eq("_id", ObjectId(id))
            )
            .toList()
            .firstOrNull()

    fun update(id: String, update: Employee): UpdateResult =
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
            .getCollection("employee", Employee::class.java)
}