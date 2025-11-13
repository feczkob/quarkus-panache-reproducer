package com.example

import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.mongo.transitions.Mongod
import de.flapdoodle.embed.mongo.transitions.RunningMongodProcess
import de.flapdoodle.embed.process.io.ProcessOutput
import de.flapdoodle.reverse.StateID
import de.flapdoodle.reverse.TransitionWalker
import de.flapdoodle.reverse.Transitions
import de.flapdoodle.reverse.transitions.Start
import io.quarkus.logging.Log
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.QuarkusTestProfile
import io.quarkus.test.junit.TestProfile
import jakarta.inject.Inject
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull
import java.time.LocalDate

@QuarkusTest
@TestProfile(DefaultTestProfile::class)
class ExampleRepositoryTest {

    @Inject
    lateinit var repository: ExampleRepository

    @Test
    fun testSomething() = runBlocking {
        // given
        val date = LocalDate.now()

        // when
        val result = repository.findTransit(date)

        // then
        assertNull(result)
    }
}

class DefaultTestProfile : QuarkusTestProfile {
    override fun testResources(): List<QuarkusTestProfile.TestResourceEntry> =
        listOf(
            QuarkusTestProfile.TestResourceEntry(
                EmbeddedMongoDb::class.java,
                emptyMap(),
                true,
            ),
        )
}

class EmbeddedMongoDb : QuarkusTestResourceLifecycleManager {
    private val transitions: Transitions = Mongod.instance()
        .withProcessOutput(
            Start.to(ProcessOutput::class.java)
                .initializedWith(ProcessOutput.silent()))
        .transitions(Version.Main.V8_0)

    private lateinit var running: TransitionWalker.ReachedState<RunningMongodProcess>

    override fun start(): Map<String, String> {
        running = transitions.walker().initState(StateID.of(RunningMongodProcess::class.java))
        val serverAddress = running.current().serverAddress
        Log.info("Started MongoDB on port: ${serverAddress.port}")

        return mapOf("quarkus.mongodb.connection-string" to "mongodb://$serverAddress/exampleDB")
    }

    override fun stop() {
        running.close()
        Log.info("Stopped MongoDB container")
    }
}
