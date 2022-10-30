package mopriestt.homepage.home_page_server.chatroom

import com.google.gson.Gson
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin("*")
@RequestMapping("/chatroom")
class ChatRoomController {
    private val gson = Gson()

    @GetMapping
    fun getChatHistory(): String {
        return gson.toJson(ChatroomRepository.fetchChatThreads())
    }

    @PostMapping
    fun postChat(@RequestParam body: Map<String, String?>): String {
        ChatroomRepository.insertChatThread(msg = body["message"] as String, body["quote_id"]?.toInt())
        return gson.toJson(ChatroomRepository.fetchChatThreads())
    }
}