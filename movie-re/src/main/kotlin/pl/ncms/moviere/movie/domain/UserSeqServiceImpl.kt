package pl.ncms.moviere.movie.domain

import pl.ncms.moviere.movie.dao.RatingDAO
import java.util.concurrent.atomic.AtomicInteger

class UserSeqServiceImpl(
        ratingDAO: RatingDAO
) : UserSeqService {
    private val nextId: AtomicInteger = AtomicInteger(ratingDAO.maxUserId())

    override fun nextId(): Int {
        val id = nextId.addAndGet(1)
        println("nextId: $id")
        return id
    }
}