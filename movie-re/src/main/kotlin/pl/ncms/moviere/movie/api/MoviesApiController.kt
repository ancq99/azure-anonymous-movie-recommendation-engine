package pl.ncms.moviere.movie.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import pl.ncms.moviere.generated.api.MoviesApi
import pl.ncms.moviere.generated.model.MovieDTO
import pl.ncms.moviere.movie.Movie
import pl.ncms.moviere.movie.dao.MovieDAO

@RestController
class MoviesApiController : MoviesApi {

    private val movieDao = MovieDAO.provide()

    override fun getMovies(name: String?): ResponseEntity<List<MovieDTO>> {
        val movies = movieDao.getAll().map { it.toDTO() }

        return ResponseEntity.ok(movies)
    }

    init {
        movieDao.create(Movie(0, "Start wars"))
        movieDao.create(Movie(0, "Start wars 2"))
    }

}