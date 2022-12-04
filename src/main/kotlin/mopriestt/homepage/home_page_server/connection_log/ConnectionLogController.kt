package mopriestt.homepage.home_page_server.connection_log

import com.maxmind.geoip2.DatabaseReader
import com.maxmind.geoip2.exception.AddressNotFoundException
import mopriestt.homepage.home_page_server.database.SqlClient
import org.springframework.util.ResourceUtils
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.InetAddress

@RestController
@CrossOrigin("*")
@RequestMapping("/connection_log")
class ConnectionLogController {
    private final val database = ResourceUtils.getFile("classpath:GeoLite2-City.mmdb")
    private final val reader = DatabaseReader.Builder(database).build()

    @PostMapping
    fun logConnection(@RequestBody ip: String) {
        val ipAddress = InetAddress.getByName(ip)
        try {
            val response = reader.city(ipAddress)
            val country = response.country.isoCode
            val regions = response.subdivisions.joinToString(",") { it.name }
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