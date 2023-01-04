package pl.ncms.moviere.movie.domain

import pl.ncms.moviere.movie.Movie
import pl.ncms.moviere.movie.Rating
import pl.ncms.moviere.movie.dao.RatingDAO

class RatingServiceImpl(
        private val ratingDAO: RatingDAO,
        private val userSeqService: UserSeqService
) : RatingService {

    override fun saveRatings(movies: List<Movie>) {
        for (movie in movies) {
            ratingDAO.create(Rating(
                    userSeqService.nextId(),
                    10,
                    movie
            ))
        }
    }
}