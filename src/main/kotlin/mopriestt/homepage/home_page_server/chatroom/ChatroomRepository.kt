package mopriestt.homepage.home_page_server.chatroom

import mopriestt.homepage.home_page_server.database.SqlClient

object ChatroomRepository {
    fun fetchChatThreads() : List<ChatThreadModel> {
        val sql = "SELECT * FROM chat_threads"
        return SqlClient.query(sql)
    }
}