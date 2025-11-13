package com.example

import io.quarkus.mongodb.panache.common.MongoEntity
import io.quarkus.mongodb.panache.kotlin.PanacheMongoEntity
import java.time.LocalDate

@MongoEntity(database = "exampleDB", collection = "examples")
class ExampleMongoEntity : PanacheMongoEntity() {
    lateinit var date: LocalDate
}
