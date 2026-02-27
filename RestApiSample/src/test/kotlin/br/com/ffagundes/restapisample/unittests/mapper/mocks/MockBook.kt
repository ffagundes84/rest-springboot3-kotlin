package br.com.ffagundes.restapisample.unittests.mapper.mocks

import br.com.ffagundes.restapisample.application.data.vo.v1.BookVO
import br.com.ffagundes.restapisample.application.data.vo.v1.PersonVO
import br.com.ffagundes.restapisample.resource.model.Book
import br.com.ffagundes.restapisample.resource.model.Person
import java.time.LocalDateTime
import java.util.ArrayList

class MockBook {
    fun mockEntity(): Book {
        return mockEntity(0)
    }

    fun mockVO(): BookVO {
        return mockVO(0)
    }

    fun mockEntityList(): ArrayList<Book> {
        val books: ArrayList<Book> = ArrayList<Book>()
        for (i in 0..10) {
            books.add(mockEntity(i))
        }
        return books
    }

    fun mockVOList(): ArrayList<BookVO> {
        val books: ArrayList<BookVO> = ArrayList()
        for (i in 0..13) {
            books.add(mockVO(i))
        }
        return books
    }

    fun mockEntity(number: Int, date: LocalDateTime = LocalDateTime.now()): Book {
        val book = Book()
        book.id = number
        book.author = "Author $number"
        book.title = "Title $number"
        book.price = number.toDouble()
        book.launchDate = date
        return book
    }

    fun mockVO(number: Int, date: LocalDateTime = LocalDateTime.now()): BookVO {
        val book = BookVO()
        book.key = number
        book.author = "Author $number"
        book.title = "Title $number"
        book.price = number.toDouble()
        book.launchDate = date
        return book
    }
}