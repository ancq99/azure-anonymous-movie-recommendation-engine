package pl.ncms.moviere.config

interface Provider<Service> {

    fun provide(): Service

}