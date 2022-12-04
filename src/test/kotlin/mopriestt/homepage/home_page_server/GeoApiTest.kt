package mopriestt.homepage.home_page_server

import com.maxmind.geoip2.DatabaseReader
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.util.ResourceUtils
import java.net.InetAddress


@SpringBootTest
class GeoApiTest {
    @Test
    fun testMyLocation() {
        val database = ResourceUtils.getFile("classpath:GeoLite2-City.mmdb")
        val reader = DatabaseReader.Builder(database).build()
        val ipAddress = InetAddress.getByName("159.196.212.234")
        val response = reader.city(ipAddress)
        val country = response.subdivisions
        println("$country ${response.city} ${response.location} ${response.postal}")
    }
}