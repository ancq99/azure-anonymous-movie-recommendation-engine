package pl.ncms.moviere.recomendation

import pl.ncms.moviere.config.EnvConfig
import pl.ncms.moviere.config.Provider
import pl.ncms.moviere.generated.model.MovieDTO
import pl.ncms.moviere.movie.dao.MovieDAO

interface RecommendationEngine {

    companion object : Provider<RecommendationEngine> {
        override fun provide(): RecommendationEngine {
            if (EnvConfig.recommendationEngineMode == RecommendationEngineMode.DUMMY) {
                return DummyRE(MovieDAO.provide())
            }
            return RecommendationEngineApi.provide()
        }
    }

    fun recommend(movies: List<MovieDTO>): List<MovieDTO>

}
