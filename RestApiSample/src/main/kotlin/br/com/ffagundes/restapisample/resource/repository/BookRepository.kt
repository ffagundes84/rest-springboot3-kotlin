package br.com.ffagundes.restapisample.resource.repository

import br.com.ffagundes.restapisample.resource.model.Book
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book, Int?>{
}