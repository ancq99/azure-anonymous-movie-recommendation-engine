package pl.ncms.moviere.movie.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import pl.ncms.moviere.generated.api.MoviesApi
import pl.ncms.moviere.generated.model.MovieDTO
import pl.ncms.moviere.movie.dao.MovieDAO
import pl.ncms.moviere.movie.domain.RatingService
import pl.ncms.moviere.recomendation.RecommendationEngine
import pl.ncms.moviere.recomendation.RecommendationEngineApi

@RestController
class MoviesApiController : MoviesApi {

    private val movieDao = MovieDAO.provide()
    private val recommendationApi = RecommendationEngine.provide()
    private val ratingService = RatingService.provide()

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

        ratingService.saveRatings(movieDTO.map {movieDao.get(it.id) ?: throw NoSuchElementException()})
        val recommendedMovies = recommendationApi.recommend(movieDTO)

        return ResponseEntity.ok(recommendedMovies)
    }

}