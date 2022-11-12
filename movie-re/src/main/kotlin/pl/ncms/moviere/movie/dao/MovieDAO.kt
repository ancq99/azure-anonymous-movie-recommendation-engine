package pl.ncms.moviere.movie.dao

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import pl.ncms.moviere.config.CrudDAO
import pl.ncms.moviere.config.Provider
import pl.ncms.moviere.movie.Movie

interface MovieDAO : CrudDAO<Movie, Int> {

    companion object : Provider<MovieDAO> {
        override fun provide(): MovieDAO {
            return StdMovieDao()
        }
    }

}

class StdMovieDao : MovieDAO {

    override fun create(entity: Movie): Int = transaction {
        val id = Movies.insert {
            if (entity.id != 0) {
                it[id] = entity.id
            }
            it[title] = entity.title
        } get Movies.id

        id
    }

    override fun update(entity: Movie): Movie {
        TODO("Not yet implemented")
    }

    override fun delete(id: Int) {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<Movie> = transaction {
        Movies.selectAll().map(this@StdMovieDao::mapToObj)
    }

    override fun get(id: Int): Movie? {
        val row = Movies.select(Movies.id eq id).single()
        return mapToObj(row)
    }

    private fun mapToObj(row: ResultRow): Movie {
        return Movie(
            row[Movies.id],
            row[Movies.title],
            row[Movies.year],
            row[Movies.isAdult] == null || row[Movies.isAdult]!! > 0,
            row[Movies.runtime],
            row[Movies.genre1],
            row[Movies.genre2],
            row[Movies.genre3],
        )
    }

}