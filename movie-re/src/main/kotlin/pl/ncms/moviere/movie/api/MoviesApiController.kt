package pl.ncms.moviere.movie.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import pl.ncms.moviere.generated.api.MoviesApi
import pl.ncms.moviere.generated.model.MovieDTO
import pl.ncms.moviere.movie.Movie
import pl.ncms.moviere.movie.dao.MovieDAO
import pl.ncms.moviere.recomendation.RecommendationEngineApi
import pl.ncms.moviere.recomendation.RecommendationEngineClient

@RestController
class MoviesApiController : MoviesApi {

    private val movieDao = MovieDAO.provide()
    private val recommendationApi = RecommendationEngineApi.provide()

    override fun getMovies(name: String?): ResponseEntity<List<MovieDTO>> {
        val movies: List<MovieDTO> = if (name == null) {
            movieDao.getAll().map { it.toDTO() }
        } else {
            movieDao.findByTitleLike(name).map { it.toDTO() }
        }

        return ResponseEntity.ok(movies)
    }

    override fun recommendMovies(movieDTO: List<MovieDTO>?): ResponseEntity<List<MovieDTO>> {
        if (movieDTO == null) {
            return ResponseEntity.badRequest().build()
        }

        return ResponseEntity.ok(
            recommendationApi.getRecommendations(movieDTO)
        )
    }

}