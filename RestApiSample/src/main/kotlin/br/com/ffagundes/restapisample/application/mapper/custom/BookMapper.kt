package br.com.ffagundes.restapisample.application.mapper.custom

import br.com.ffagundes.restapisample.application.data.vo.v1.BookVO
import br.com.ffagundes.restapisample.application.data.vo.v2.PersonVO
import br.com.ffagundes.restapisample.resource.model.Book
import br.com.ffagundes.restapisample.resource.model.Person
import java.util.*

object BookMapper {
    fun voToEntity(bookVO: BookVO): Book {
        return Book(
            id = bookVO.key,
            author = bookVO.author,
            launchDate = bookVO.launchDate,
            price = bookVO.price,
            title = bookVO.title
        )
    }
    fun entityToVO(entity: Book): BookVO {
        return BookVO(
            key = entity.id,
            author = entity.author,
            launchDate = entity.launchDate!!,
            price = entity.price,
            title = entity.title
        )
    }
}