package br.com.ffagundes.restapisample.integrationtests.vo

import jakarta.xml.bind.annotation.XmlRootElement
import java.util.Date

@XmlRootElement
data class BookVO (
    var id: Long = 0,
    var author: String = "",
    var launchDate: Date = Date(),
    var price: Double = 0.0,
    var title: String = ""
)