package wang.ralph.blog.demo.plugins

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        jackson()
    }
}
