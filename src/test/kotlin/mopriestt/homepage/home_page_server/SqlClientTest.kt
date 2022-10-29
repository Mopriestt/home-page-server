package mopriestt.homepage.home_page_server

import mopriestt.homepage.home_page_server.chatroom.ChatThreadModel
import mopriestt.homepage.home_page_server.database.SqlClient
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SqlClientTest {
    @Test
    fun testSqlClient() {
        SqlClient.update(SqlTest.DROP_TEST_TABLE)
        SqlClient.update(SqlTest.CREATE_TEST_TABLE)
        SqlClient.update(SqlTest.INSERT_ONE)
        SqlClient.update(SqlTest.INSERT_TWO)
        SqlClient.update(SqlTest.REPLY_ONE)
        SqlClient.update(SqlTest.REPLY_TWO)

        val rows = SqlClient.query<ChatThreadModel>(SqlTest.SELECT_ALL)

        assert(rows.size == 4)
        assert(rows[0].message == "这是第一条")
        assert(rows[1].userId == null)
        assert(rows[2].quoteId == 1)
        assert(rows[3].threadId == 4)
    }
}