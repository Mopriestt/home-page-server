package mopriestt.homepage.home_page_server.chatroom

import mopriestt.homepage.home_page_server.database.SqlClient
import mopriestt.homepage.home_page_server.database.sanitizeSqlString

object ChatroomRepository {
    fun fetchChatThreads(): List<ChatThreadModel> {
        val sql = "SELECT * FROM chat_threads"
        return SqlClient.query(sql)
    }

    fun insertChatThread(msg: String, quoteId: Int? = null) {
        val escapedMsg = sanitizeSqlString(msg)
        val sql = "INSERT INTO chat_threads(quote_id,message) VALUES($quoteId, '$escapedMsg')"
        SqlClient.update(sql)
    }
}
