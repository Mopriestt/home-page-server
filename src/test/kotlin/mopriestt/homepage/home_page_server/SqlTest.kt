package mopriestt.homepage.home_page_server

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.sql.DriverManager

@SpringBootTest
class SqlTest {

    @Test
    fun sqlConnectionTest() {
        val connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HOMEPAGE_TEST", "root", "123456")
        val statement = connection.createStatement()

        statement.use {
            statement.executeUpdate(DROP_TEST_TABLE)
            statement.executeUpdate(CREATE_TEST_TABLE)

            statement.executeUpdate(INSERT_ONE)
            statement.executeUpdate(INSERT_TWO)

            var resultSet = statement.executeQuery("SELECT * FROM chat_threads")
            assert(resultSet.next())
            assert(resultSet.getInt("thread_id") == 1)
            assert(resultSet.getInt("quote_id") == 0)
            assert(resultSet.getString("message") == "这是第一条")
            assert(resultSet.next())
            assert(resultSet.getInt("thread_id") == 2)
            assert(resultSet.getString("message") == "this is second thread")
            assert(!resultSet.next())
            resultSet.close()

            statement.executeUpdate(REPLY_ONE)
            statement.executeUpdate(REPLY_TWO)

            resultSet = statement.executeQuery("SELECT * FROM chat_threads WHERE thread_id > 2")
            assert(resultSet.next())
            assert(resultSet.getInt("thread_id") == 3)
            assert(resultSet.getInt("quote_id") == 1)
            assert(resultSet.getString("message") == "回复第一条")
            assert(resultSet.next())
            assert(resultSet.getInt("thread_id") == 4)
            assert(resultSet.getInt("quote_id") == 2)
            assert(resultSet.getString("message") == "reply to second thread")
            assert(!resultSet.next())
            resultSet.close()

            statement.executeUpdate(DROP_TEST_TABLE)
        }

        connection.close()
    }

    @Test
    fun queryRacingTest() {
        val connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HOMEPAGE_TEST", "root", "123456")
        val statement = connection.createStatement()
        val statement2 = connection.createStatement()

        statement.use {
            statement.executeUpdate(DROP_TEST_TABLE)
            statement.executeUpdate(CREATE_TEST_TABLE)

            statement.executeUpdate(INSERT_ONE)
            statement.executeUpdate(INSERT_TWO)

            val resultSet = statement.executeQuery(SELECT_ALL)

            for (i in 1.. 100) {
                statement2.executeUpdate(INSERT_ONE)
            }
            while (resultSet.next()) println(resultSet)
        }
    }

    companion object {
        const val DROP_TEST_TABLE = "DROP TABLE IF EXISTS chat_threads"
        const val CREATE_TEST_TABLE =
            """
                CREATE TABLE chat_threads(
                    thread_id INT NOT NULL AUTO_INCREMENT,
                    user_id INT NULL,
                    post_time DATETIME DEFAULT(UTC_TIMESTAMP) NOT NULL,
                    quote_id INT NULL,
                    message TEXT,
                    PRIMARY KEY(thread_id)
                )
            """
        const val INSERT_ONE = "INSERT INTO chat_threads(message) values('这是第一条')"
        const val INSERT_TWO = "INSERT INTO chat_threads(message) values('this is second thread')"
        const val REPLY_ONE = "INSERT INTO chat_threads(quote_id, message) values(1, '回复第一条')"
        const val REPLY_TWO = "INSERT INTO chat_threads(quote_id, message) values(2, 'reply to second thread')"
        const val SELECT_ALL = "SELECT * FROM chat_threads"
    }
}
