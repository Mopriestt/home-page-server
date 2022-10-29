package mopriestt.homepage.home_page_server.chatroom

import mopriestt.homepage.home_page_server.database.Column
import java.time.LocalDateTime

data class ChatThreadModel(

    @Column("thread_id")
    val threadId: Int,

    @Column("user_id")
    val userId: Int?,

    @Column("post_time")
    val postTime: LocalDateTime,

    @Column("quote_id")
    val quoteId: Int?,

    @Column("message")
    val message: String,
)