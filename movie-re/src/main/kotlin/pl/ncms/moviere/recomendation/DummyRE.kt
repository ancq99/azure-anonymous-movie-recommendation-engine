package pl.ncms.moviere.recomendation

import pl.ncms.moviere.generated.model.MovieDTO
import pl.ncms.moviere.movie.api.toDTO
import pl.ncms.moviere.movie.dao.MovieDAO
import java.util.Random

class DummyRE(
    private val movieDAO: MovieDAO
) : RecommendationEngine {

    override fun recommend(movies: List<MovieDTO>): List<MovieDTO> {
        val pageId = Random().nextInt(100)
        return movieDAO.getAllPageable(10, pageId)
            .map { it.toDTO() }
    }

}