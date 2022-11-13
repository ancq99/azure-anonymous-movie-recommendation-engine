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
        val movies: List<MovieDTO> = if (name == null) {
            movieDao.getAll().map { it.toDTO() }
        } else {
            movieDao.findByTitleLike(name).map { it.toDTO() }
        }

        return ResponseEntity.ok(movies)
    }

    private var cache: List<MovieDTO>? = null
    override fun recommendMovies(movieDTO: List<MovieDTO>?): ResponseEntity<List<MovieDTO>> {
        if (cache == null) {
            cache = movieDao.getAll().subList(0, 10).map { it.toDTO() }
        }

        return ResponseEntity.ok(cache)
    }

    init {
        movieDao.create(Movie(0, "Start wars"))
        movieDao.create(Movie(0, "Start wars 2"))
    }

}