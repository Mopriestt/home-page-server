package mopriestt.homepage.home_page_server.chatroom

import com.google.gson.Gson
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
    private val gson = Gson()
    val chats = mutableListOf<String>()

    @GetMapping
    fun getChatHistory(): String = gson.toJson(ChatroomRepository.fetchChatThreads())

    @PostMapping
    fun postMessage(@RequestBody msg: String): List<String> {
        return chats.apply { this += msg }
    }
}