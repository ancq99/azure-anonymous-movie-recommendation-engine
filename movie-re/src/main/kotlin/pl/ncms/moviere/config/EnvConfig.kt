package pl.ncms.moviere.config

object EnvConfig {

    val dbHost = notEmptyOrDefault(System.getenv("DB_HOST"), "localhost:5432")
    val dbPassword = notEmptyOrDefault(System.getenv("DB_PASSWORD"), "123")
    val dbUsername = notEmptyOrDefault( System.getenv("DB_USERNAME"), "postgres")
    val dbDatabase = notEmptyOrDefault( System.getenv("DB_DATABASE"), "postgres")

    val movieRecommendationEngineApiUrl = notEmptyOrDefault("MRE_API_URL", "http://20.101.184.1:80/api/v1/service/sdafasd/score")

    private fun notEmptyOrDefault(str: String, default: String): String {
        return if (str != "") {
            str
        } else {
            default
        }
    }

}