package pl.ncms.moviere.movie.dao

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction

class CrewMembers : Table(name = "castlist") {
    val id = integer("id")
    val name = varchar("name", 10)
    val character = varchar("character", 250)
    val birth = varchar("birth", 4)
    val death = varchar("death", 4)

    val movieId = (integer("castlistfk") references Movies.id)

    override val primaryKey = PrimaryKey(id, name = "PK_CREW_ID")

}