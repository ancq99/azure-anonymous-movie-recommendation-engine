package pl.ncms.moviere.config.db

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.vendors.PostgreSQLDialect
import org.springframework.context.annotation.Configuration
import pl.ncms.moviere.movie.dao.initCrew
import pl.ncms.moviere.movie.dao.initMovies

@Configuration
class DBConfig {

    init {
        connect()
        init()
    }

    private final fun connect() {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/postgres",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "123"
        )
    }

    private final fun init() {
        initMovies()
        initCrew()
    }

}
