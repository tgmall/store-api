package wang.ralph.blog.demo

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import wang.ralph.blog.demo.plugins.configureMonitoring
import wang.ralph.blog.demo.plugins.configureRouting
import wang.ralph.blog.demo.plugins.configureSecurity
import wang.ralph.blog.demo.plugins.configureSerialization

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSecurity()
        configureMonitoring()
        configureSerialization()
    }.start(wait = true)
}
