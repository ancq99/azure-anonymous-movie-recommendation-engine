package pl.ncms.moviere.movie.dao

import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import pl.ncms.moviere.config.Provider
import pl.ncms.moviere.movie.Rating

interface RatingDAO {

    companion object : Provider<RatingDAO> {
        override fun provide(): RatingDAO {
            return StdRatingDAO()
        }
    }

    fun create(entity: Rating): Int

    fun maxUserId(): Int

}

class StdRatingDAO : RatingDAO {

    override fun create(entity: Rating): Int = transaction {
        Ratings.insert {
            if (entity.id != 0) {
                it[id] = entity.id
            }
            it[userId] = entity.userId
            it[rating] = entity.rating
            it[ratingListFK] = entity.movie.id
        }[Ratings.id]
    }

    override fun maxUserId(): Int = transaction {
        Ratings.selectAll()
                .orderBy(Ratings.userId, SortOrder.DESC)
                .limit(1)
                .first()[Ratings.userId]
    }
}