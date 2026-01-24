package br.com.ffagundes.restapisample.application.data.vo.v2

import java.util.Date

data class PersonVO(
    var id: Int = 0,
    var firstName: String = "",
    var lastName: String = "",
    var address: String = "",
    var gender: String = "",
    var birthDay: Date? = null
)
