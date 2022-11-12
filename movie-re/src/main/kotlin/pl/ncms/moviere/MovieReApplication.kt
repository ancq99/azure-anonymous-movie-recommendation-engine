package pl.ncms.moviere

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MovieReApplication

fun main(args: Array<String>) {
    runApplication<MovieReApplication>(*args)
}
