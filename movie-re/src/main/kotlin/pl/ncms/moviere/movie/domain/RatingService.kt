package pl.ncms.moviere.movie.domain

import pl.ncms.moviere.movie.Movie
import pl.ncms.moviere.config.Provider
import pl.ncms.moviere.movie.dao.RatingDAO

interface RatingService {

    companion object : Provider<RatingService> {
        override fun provide(): RatingService {
            return RatingServiceImpl(RatingDAO.provide(), UserSeqService.provide())
        }
    }

    fun saveRatings(movies: List<Movie>)

}