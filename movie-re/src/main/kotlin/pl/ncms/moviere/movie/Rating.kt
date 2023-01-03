package pl.ncms.moviere.movie

class Rating(
        val id: Int = 0,
        val userId: Int,
        val rating: Int,
        val movie: Movie
) {

    constructor(userId: Int, rating: Int, movie: Movie) :
            this(0, userId, rating, movie)


}