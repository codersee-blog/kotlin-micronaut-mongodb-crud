package com.codersee.model

import io.micronaut.core.annotation.Creator
import io.micronaut.core.annotation.Introspected
import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

@Introspected
data class Company @Creator @BsonCreator constructor(
    @field:BsonProperty("_id") @param:BsonProperty("_id") var id: ObjectId? = null,
    @field:BsonProperty("name") @param:BsonProperty("name") var name: String,
    @field:BsonProperty("address") @param:BsonProperty("address") var address: String
)