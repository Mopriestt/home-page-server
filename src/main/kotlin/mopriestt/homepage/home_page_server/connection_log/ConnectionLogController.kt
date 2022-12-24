package mopriestt.homepage.home_page_server.connection_log

import com.google.gson.Gson
import com.maxmind.geoip2.DatabaseReader
import com.maxmind.geoip2.exception.AddressNotFoundException
import mopriestt.homepage.home_page_server.database.SqlClient
import org.springframework.util.ResourceUtils
import org.springframework.web.bind.annotation.*
import java.io.File
import java.lang.Exception
import java.net.InetAddress

@RestController
@CrossOrigin("*")
@RequestMapping("/connection_log")
class ConnectionLogController {
    private val gson = Gson()

    private final val database = try {
        // For Jar
        File("GeoLite2-City.mmdb").also {
            if (!it.canRead()) throw Exception()
        }
    } catch (e: Exception) {
        // For IDE development
        ResourceUtils.getFile("classpath:GeoLite2-City.mmdb")
    }
    private final val reader = DatabaseReader.Builder(database).build()

    @GetMapping
    fun getConnectionLogs() = gson.toJson(
        SqlClient.query<ConnectionLogModel>("SELECT * FROM connection_log ORDER BY connection_id DESC LIMIT 100")
    )

    @PostMapping
    fun logConnection(@RequestBody ip: String) {
        val ipAddress = InetAddress.getByName(ip)
        try {
            val response = reader.city(ipAddress)
            val country = response.country.isoCode
            val regions = response.subdivisions.joinToString(",") { it.name }.ifEmpty { "null" }
            val city = response.city.name
            val latitude = response.location.latitude
            val longitude = response.location.longitude
            val postal = response.postal.code

            val sql = "INSERT INTO connection_log(ip,country,region,city,latitude,longitude,postal) " +
                    "VALUES('$ip','$country','$regions','$city','$latitude','$longitude','$postal')"
            SqlClient.update(sql)
        } catch (e: AddressNotFoundException) {
            println("Connection Log: $ip not in geolocation db!")
        }
    }
}