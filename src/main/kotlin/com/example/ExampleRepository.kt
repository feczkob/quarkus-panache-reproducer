package com.example

import io.quarkus.mongodb.panache.kotlin.reactive.ReactivePanacheMongoRepository
import io.smallrye.mutiny.coroutines.awaitSuspending
import jakarta.enterprise.context.ApplicationScoped
import java.time.LocalDate

@ApplicationScoped
class ExampleRepository : ReactivePanacheMongoRepository<ExampleMongoEntity> {
    suspend fun findTransit(
        date: LocalDate,
    ): ExampleMongoEntity? {
        return find(
            "date = ?1",
            date,
        ).firstResult().awaitSuspending()
    }
}
