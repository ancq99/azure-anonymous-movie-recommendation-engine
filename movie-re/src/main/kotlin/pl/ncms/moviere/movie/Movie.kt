package pl.ncms.moviere.movie

class Movie(
    val id: Int,
    val title: String,
    val year: String? = null,
    val isAdult: Boolean = false,
    val runtime: String? = null,
    val genre1: String? = null,
    val genre2: String? = null,
    val genre3: String? = null,
    val crewNumber: Int? = null,
    val castNumber: Int? = null,
    val avgRating: Double? = null,
    val numOfVotes: Int? = null
) {



}