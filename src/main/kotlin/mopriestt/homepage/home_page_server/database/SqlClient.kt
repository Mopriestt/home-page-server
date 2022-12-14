package mopriestt.homepage.home_page_server.database

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

const val DATASOURCE_URL = "jdbc:mysql://localhost:3306/HOMEPAGE"
private const val TEST_DATASOURCE_URL = "jdbc:mysql://localhost:3306/HOMEPAGE_TEST"
const val USER_NAME = "root"
const val PWD = "123456"

object SqlClient {
    var testConnection: Connection? = null

    private var connectionInstance: Connection? = null

    val connection: Connection
        get() {
            if (connectionInstance?.isValid(5) != true) {
                connectionInstance = DriverManager.getConnection(DATASOURCE_URL, USER_NAME, PWD)
            }
            return connectionInstance!!
        }

    inline fun <reified T> query(sql: String): List<T> {
        val statement = (testConnection ?: connection).createStatement()
        statement.use {
            val resultSet = statement.executeQuery(sql)
            val resultList = mutableListOf<T>()
            while (resultSet.next()) {
                resultList += resultSet.toModels<T>()
            }
            return resultList
        }
    }

    fun update(sql: String) = (testConnection ?: connection).createStatement().use { it.executeUpdate(sql) }

    fun initTestEnv() {
        connection.close()
        testConnection = DriverManager.getConnection(TEST_DATASOURCE_URL, USER_NAME, PWD)
    }
}

inline fun <reified T> ResultSet.toModels(): T {
    require(T::class.isData)

    val constructor = T::class.java.constructors.first()
    val args = mutableListOf<Any?>()
    T::class.java.declaredFields.forEach {
        assert(it.declaredAnnotations.any { annotation -> annotation is Column })

        args += getObject(it.getDeclaredAnnotation(Column::class.java).columnLabel)
    }
    return constructor.newInstance(*args.toTypedArray()) as T
}

fun sanitizeSqlString(str: String) = str.replace("\\", "\\\\").replace("'", "\\'")