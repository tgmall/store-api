package wang.ralph.store.application.configuration

import wang.ralph.store.models.captcha.randomText

object JwtConfiguration {
    val secret = System.getenv("jwt.secret") ?: randomText(10)
    val issuer = System.getenv("jwt.issuer") ?: "http://0.0.0.0"
    val audience = System.getenv("jwt.audience") ?: "http://0.0.0.0/store"
    val realm = System.getenv("jwt.realm") ?: "store"
}
