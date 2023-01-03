package pl.ncms.moviere.movie.dao

import org.jetbrains.exposed.sql.Table

object Ratings : Table("ratinglist") {
    val id = integer("id").autoIncrement()
    val userId = integer("userid")
    val rating = integer("rating")

    val ratingListFK = (integer("ratinglistfk") references Movies.id)
    override val primaryKey = PrimaryKey(id, name = "pk_ratinglist")

}
