package pl.ncms.moviere.recomendation

import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.web.client.RestTemplate
import pl.ncms.moviere.config.EnvConfig
import pl.ncms.moviere.config.Provider
import pl.ncms.moviere.generated.model.MovieDTO
import pl.ncms.moviere.movie.api.toDTO
import pl.ncms.moviere.movie.dao.MovieDAO

interface RecommendationEngineApi : RecommendationEngine {

    companion object : Provider<RecommendationEngineApi> {

        override fun provide(): RecommendationEngineApi {
            return RecommendationEngineClient(
                RestTemplate(),
                EnvConfig.movieRecommendationEngineApiUrl,
                MovieDAO.provide()
            )
        }

    }

    override fun recommend(movies: List<MovieDTO>): List<MovieDTO>

}

class RecommendationEngineClient(
    private val httpClient: RestTemplate,
    private val url: String,
    private val movieRepo: MovieDAO
) : RecommendationEngineApi {

    override fun recommend(movies: List<MovieDTO>): List<MovieDTO> {
        val response = httpClient.exchange(
            RequestEntity.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(movies.map { movie -> movie.title }),
            Map::class.java
        )

        val bodyValues = response.body?.values ?: throw IllegalStateException()

        return bodyValues.map {
            movieRepo.findByTitle(it as String)[0].toDTO()
        }
    }

}