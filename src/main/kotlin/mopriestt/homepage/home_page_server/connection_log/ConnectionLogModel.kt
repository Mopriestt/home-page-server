package mopriestt.homepage.home_page_server.connection_log

import mopriestt.homepage.home_page_server.database.Column
import java.math.BigDecimal
import java.time.LocalDateTime

data class ConnectionLogModel(

    @Column("connection_id")
    val id: Int,

    @Column("connection_time")
    val connectionTime: LocalDateTime,

    @Column("ip")
    val ip: String,

    @Column("country")
    val country: String,

    @Column("region")
    val region: String,

    @Column("city")
    val city: String,

    @Column("latitude")
    val latitude: BigDecimal,

    @Column("longitude")
    val longitude: BigDecimal,

    @Column("postal")
    val postal: String,
)