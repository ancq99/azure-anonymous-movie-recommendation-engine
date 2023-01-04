package pl.ncms.moviere.config

import org.slf4j.LoggerFactory
import pl.ncms.moviere.recomendation.RecommendationEngine
import pl.ncms.moviere.recomendation.RecommendationEngineMode
import pl.ncms.moviere.recomendation.RecommendationEngineMode.DUMMY

object EnvConfig {

    private val logger = LoggerFactory.getLogger(EnvConfig::class.java)

    val dbHost = getOSEnvOrDefault("DB_HOST", "localhost:5432")
    val dbPassword = getOSEnvOrDefault("DB_PASSWORD", "123")
    val dbUsername = getOSEnvOrDefault( "DB_USERNAME", "postgres")
    val dbDatabase = getOSEnvOrDefault( "DB_DATABASE", "postgres")

    val recommendationEngineMode = RecommendationEngineMode.valueOf(
        getOSEnvOrDefault("RE_MODE", DUMMY.name)
    )
    val movieRecommendationEngineApiUrl = getOSEnvOrDefault("MRE_API_URL", "http://20.101.184.1:80/api/v1/service/sdafasd/score")

    private fun getOSEnvOrDefault(osEnv: String, default: String): String {
        val envVal = System.getenv(osEnv)

        return if (envVal == null || envVal == "") {
            logger.info("${osEnv}=NULL (fallback to default: ${default})")
            default
        } else {
            logger.info("${osEnv}=${envVal}")
            envVal
        }
    }

//    init {
//        val values = System.getenv()
//        for (key in values.keys)
//        {
//            println("${key}=${values[key]}")
//        }
//    }

}

