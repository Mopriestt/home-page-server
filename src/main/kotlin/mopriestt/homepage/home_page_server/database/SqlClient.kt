package mopriestt.homepage.home_page_server.database

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

private const val DATASOURCE_URL = "jdbc:mysql://localhost:3306/HOMEPAGE"
private const val USER_NAME = "root"
private const val PWD = "123456"

object SqlClient {
    val connection: Connection = DriverManager.getConnection(DATASOURCE_URL, USER_NAME, PWD)

    inline fun <reified T> query(sql: String): List<T> {
        val statement = connection.createStatement()
        statement.use {
            val resultSet = statement.executeQuery(sql)
            val resultList = mutableListOf<T>()
            while (resultSet.next()) {
                resultList += resultSetToModel<T>(resultSet)
            }
            return resultList
        }
    }

    fun update(sql: String) = connection.createStatement().use { it.executeUpdate(sql) }
}

inline fun <reified T> resultSetToModel(resultSet: ResultSet) : T {
    require(T::class.isData)

    val constructor= T::class.java.constructors.first()
    val args = mutableListOf<Any?>()
    T::class.java.declaredFields.forEach {
        assert(it.declaredAnnotations.any { annotation -> annotation is Column })

        args += resultSet.getObject(it.getDeclaredAnnotation(Column::class.java).columnLabel)
    }
    return constructor.newInstance(*args.toTypedArray()) as T
}