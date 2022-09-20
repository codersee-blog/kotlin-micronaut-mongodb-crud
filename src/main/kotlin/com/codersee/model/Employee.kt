package com.codersee.model

import io.micronaut.core.annotation.Introspected
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

@Introspected
data class Employee(
    @field:BsonProperty("_id") @param:BsonProperty("_id") var id: ObjectId? = null,
    @field:BsonProperty("firstName") @param:BsonProperty("firstName") var firstName: String,
    @field:BsonProperty("lastName") @param:BsonProperty("lastName") var lastName: String,
    @field:BsonProperty("email") @param:BsonProperty("email") var email: String,
    @field:BsonProperty("company") @param:BsonProperty("company") var company: Company?,
)