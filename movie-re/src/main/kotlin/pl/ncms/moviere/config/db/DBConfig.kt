package pl.ncms.moviere.config.db

import org.jetbrains.exposed.sql.Database
import org.springframework.context.annotation.Configuration
import pl.ncms.moviere.config.EnvConfig

@Configuration
class DBConfig {

    init {
        connect()
    }

    private final fun connect() {
        Database.connect(
            url = "jdbc:postgresql://${EnvConfig.dbHost}/${EnvConfig.dbDatabase}",
            driver = "org.postgresql.Driver",
            user = EnvConfig.dbUsername,
            password = EnvConfig.dbPassword,
        )
    }

}
