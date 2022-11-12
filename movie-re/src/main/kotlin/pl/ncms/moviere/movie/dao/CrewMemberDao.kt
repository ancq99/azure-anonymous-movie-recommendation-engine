package pl.ncms.moviere.movie.dao

import pl.ncms.moviere.config.CrudDAO
import pl.ncms.moviere.config.Provider
import pl.ncms.moviere.movie.CrewMember

interface CrewMemberDao : CrudDAO<CrewMember, Int> {
    companion object : Provider<CrewMemberDao> {
        override fun provide(): CrewMemberDao {
            return StdCrewMemberDao()
        }
    }
}

class StdCrewMemberDao : CrewMemberDao {

    override fun create(entity: CrewMember): Int {
        TODO("Not yet implemented")
    }

    override fun update(entity: CrewMember): CrewMember {
        TODO("Not yet implemented")
    }

    override fun delete(id: Int) {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<CrewMember> {
        TODO("Not yet implemented")
    }

    override fun get(id: Int): CrewMember? {
        TODO("Not yet implemented")
    }
}