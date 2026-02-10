package br.com.ffagundes.restapisample.application.mapper.custom

import br.com.ffagundes.restapisample.application.data.vo.v2.PersonVO
import br.com.ffagundes.restapisample.resource.model.Person
import java.util.*

object PersonMapper {
    fun voToEntity(personVO: PersonVO): Person {
        return Person(
            id = personVO.key,
            firstName = personVO.firstName,
            lastName = personVO.lastName,
            address = personVO.address,
            gender = personVO.gender
        )
    }
    fun entityToVO(entity: Person): PersonVO {
        return PersonVO(
            key = entity.id,
            firstName = entity.firstName,
            lastName = entity.lastName,
            address = entity.address,
            gender = entity.gender,
            birthDay = Date()
        )
    }
}