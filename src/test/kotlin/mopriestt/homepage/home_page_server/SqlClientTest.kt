package mopriestt.homepage.home_page_server

import mopriestt.homepage.home_page_server.chatroom.ChatThreadModel
import mopriestt.homepage.home_page_server.chatroom.ChatroomRepository
import mopriestt.homepage.home_page_server.database.SqlClient
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SqlClientTest {
    @BeforeAll
    fun setup() = SqlClient.initTestEnv()

    @BeforeEach
    fun initDatabase() {
        SqlClient.update(SqlTest.DROP_TEST_TABLE)
        SqlClient.update(SqlTest.CREATE_TEST_TABLE)
    }

    @AfterEach
    fun tearDown() {
        SqlClient.update(SqlTest.DROP_TEST_TABLE)
    }

    @Test
    fun testSqlClient() {
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

    @Test
    fun testRepoSql() {
        ChatroomRepository.insertChatThread("第一条")
        Thread.sleep(500)
        ChatroomRepository.insertChatThread("second")
        Thread.sleep(500)
        ChatroomRepository.insertChatThread("回复第一条", quoteId = 1)
        Thread.sleep(500)
        ChatroomRepository.insertChatThread("reply second", quoteId = 2)
        Thread.sleep(500)

        val rows = ChatroomRepository.fetchChatThreads()

        assert(rows.size == 4)
        assert(rows[0].message == "第一条")
        assert(rows[1].userId == null)
        assert(rows[2].quoteId == 1)
        assert(rows[3].threadId == 4)
    }
}