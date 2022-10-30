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

    @GetMapping
    fun getChatHistory(): String = gson.toJson(ChatroomRepository.fetchChatThreads())

    @PostMapping
    fun postChat(@RequestBody body: Map<String, Any>): String {
        ChatroomRepository.insertChatThread(msg = body["message"] as String, quoteId = body["quote_id"] as Int?)
        return gson.toJson(ChatroomRepository.fetchChatThreads())
    }
}