package pl.ncms.moviere.movie.domain

import pl.ncms.moviere.config.Provider
import pl.ncms.moviere.movie.dao.RatingDAO

interface UserSeqService {

    companion object : Provider<UserSeqService> {
        override fun provide(): UserSeqService {
            return UserSeqServiceImpl(RatingDAO.provide())
        }
    }

    fun nextId(): Int

}