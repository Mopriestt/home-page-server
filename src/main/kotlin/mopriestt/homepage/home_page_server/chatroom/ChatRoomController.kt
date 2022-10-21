package mopriestt.homepage.home_page_server.chatroom

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin("*")
@RequestMapping("/chatroom")
class ChatRoomController {
  val chats = mutableListOf<String>();
  @GetMapping
  fun getChatHistory(): List<String> {
    return chats
  }
  @PostMapping
  fun postMessage(@RequestBody msg: String): List<String> {
    return chats.apply { this += msg }
  }
}