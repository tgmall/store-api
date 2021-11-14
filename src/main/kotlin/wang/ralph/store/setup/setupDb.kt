package wang.ralph.store.setup

import org.jetbrains.exposed.sql.Database

fun setupDb() {
    val url = "jdbc:mysql://dev:dev@localhost:3306/store?useUnicode=true&serverTimezone=UTC"
    val driver = "com.mysql.cj.jdbc.Driver"
    Database.connect(url, driver)
}
