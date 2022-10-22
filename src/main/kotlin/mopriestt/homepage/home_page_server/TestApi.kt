package mopriestt.homepage.home_page_server

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("ping")
@CrossOrigin("*")
class TestApi {
    @GetMapping
    fun ping() = "Pong!"
}
