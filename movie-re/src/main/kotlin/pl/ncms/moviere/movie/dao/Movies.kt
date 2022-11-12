package pl.ncms.moviere.movie.dao

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction

object Movies : Table() {
    val id = integer("movie_id").autoIncrement()
    val title = varchar("title", 500)
    val year = varchar("year", 4).nullable()
    val isAdult = integer("isadult").nullable()
    val runtime = varchar("runtime", 10).nullable()
    val genre1 = varchar("genre1", 50).nullable()
    val genre2 = varchar("genre2", 50).nullable()
    val genre3 = varchar("genre3", 50).nullable()
    val crewNumber = integer("crewnumber").nullable()
    val castNumber = integer("castnumber").nullable()
    val avgRating = double("avgrating").nullable()
    val numOfVotes = integer("numofvotes").nullable()

    override val primaryKey = PrimaryKey(id, name = "PK_MOVIES_ID")

}

fun initMovies() = transaction {
    SchemaUtils.drop(Movies)
    SchemaUtils.create(Movies)
}