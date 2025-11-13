# Quarkus Panache issue reproducer

The error is
```
java.lang.IllegalStateException: This method is normally automatically overridden in subclasses
	at io.quarkus.mongodb.panache.common.reactive.runtime.ReactiveMongoOperations.implementationInjectionMissing(ReactiveMongoOperations.java:763)
	at io.quarkus.mongodb.panache.kotlin.reactive.ReactivePanacheMongoRepositoryBase.find(ReactivePanacheMongoRepositoryBase.kt:77)
```

It appeared with Quarkus 3.29, the previous 3.28.5 version works fine.

## Reproduction
Run `./mvnw clean verify` to see the error, or run `ExampleRepositoryTest` directly from your IDE.
