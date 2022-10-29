package mopriestt.homepage.home_page_server

import mopriestt.homepage.home_page_server.chatroom.ChatThreadModel
import mopriestt.homepage.home_page_server.database.Column
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ReflectTests {
    @Test
    fun testGetFieldsAnnotation() {

        val fields = ChatThreadModel::class.java.declaredFields
        fields.forEach {
            println("--------------")
            println(it.name)
            println(it.type)
            it.declaredAnnotations.forEach { annotation ->
                println((annotation as Column).columnLabel)
                println(annotation)
            }
        }
    }

    @Test
    fun testConstructor() {
        val constructor = ChatThreadModel::class.java.constructors.first()
        constructor.parameters.forEach {
            println("=======================")
            println(it)
            it.annotations.forEach { annotation ->
                println(annotation)
            }
            it.declaredAnnotations.forEach { annotation ->
                println(annotation)
            }
        }
    }
}
