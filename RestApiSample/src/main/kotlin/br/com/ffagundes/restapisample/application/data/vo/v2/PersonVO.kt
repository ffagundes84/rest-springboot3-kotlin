package br.com.ffagundes.restapisample.application.data.vo.v2

import org.springframework.hateoas.RepresentationModel

import java.util.Date

data class PersonVO(
    var key: Int = 0,
    var firstName: String = "",
    var lastName: String = "",
    var address: String = "",
    var gender: String = "",
    var birthDay: Date? = null
)
