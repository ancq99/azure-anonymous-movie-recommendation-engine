package pl.ncms.moviere.movie.api

import pl.ncms.moviere.generated.model.MovieDTO
import pl.ncms.moviere.movie.Movie

fun Movie.toDTO(): MovieDTO {
    return MovieDTO(
        id, title
    )
}

fun MovieDTO.toDomain(): Movie {
    return Movie(
        id, title
    )
}