package pl.ncms.moviere.movie

class CrewMember(
    val id: Long,
    var name: String
) {

    var character: String? = null
    var birth: String? = null
    var death: String? = null

}