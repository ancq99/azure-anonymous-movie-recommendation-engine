package pl.ncms.moviere.config

interface CrudDAO<ENTITY, ID> {

    fun create(entity: ENTITY): ID

    fun update(entity: ENTITY): ENTITY

    fun delete(id: ID)

    fun getAll(): List<ENTITY>

    fun get(id: ID): ENTITY?

}