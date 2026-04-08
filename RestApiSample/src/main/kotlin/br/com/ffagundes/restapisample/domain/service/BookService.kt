package br.com.ffagundes.restapisample.domain.service

import br.com.ffagundes.restapisample.application.controller.BookController
import br.com.ffagundes.restapisample.application.data.vo.v1.BookVO
import br.com.ffagundes.restapisample.application.exceptions.RequiredObjectIsNullException
import br.com.ffagundes.restapisample.application.exceptions.ResourceNotFoundException
import br.com.ffagundes.restapisample.application.mapper.DozerMapper
import br.com.ffagundes.restapisample.resource.model.Book
import br.com.ffagundes.restapisample.resource.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class BookService {
    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var pageResourceAssembler: PagedResourcesAssembler<BookVO>

    private val logger = Logger.getLogger(BookService::class.java.name)
    fun findById(id: Int): BookVO {
        logger.info("finding book by id: $id")
        val book = bookRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("No record found for id $id") }
        val bookVO = DozerMapper.parseObject(book, BookVO::class.java)
        val withSelRel = linkTo(BookController::class.java).slash(bookVO.key).withSelfRel()
        bookVO.add(withSelRel)
        return bookVO
    }

    fun findAll(pageable: Pageable): PagedModel<EntityModel<BookVO>> {
        logger.info("finding all")
        val books = bookRepository.findAll(pageable)
        val booksVO = books.map { DozerMapper.parseObject(it, BookVO::class.java) }
        booksVO.map {
            it.add(linkTo(BookController::class.java).slash(it.key).withSelfRel())
        }
        return pageResourceAssembler.toModel(booksVO)
    }

    fun create(book: BookVO?) : BookVO {
        if (book == null) throw RequiredObjectIsNullException()
        logger.info("saving one book with title: ${book.title}")
        val entity: Book = DozerMapper.parseObject(book, Book::class.java)
        logger.info("Book entity object: $entity")
        val bookVO: BookVO = DozerMapper.parseObject(bookRepository.save(entity), BookVO::class.java)
        logger.info("Book VO object: $bookVO")
        val withSelRel = linkTo(BookController::class.java).slash(bookVO.key).withSelfRel()
        bookVO.add(withSelRel)
        return bookVO
    }
    fun update(book: BookVO?) : BookVO {
        if (book == null) throw RequiredObjectIsNullException()
        logger.info("updating one book with title: ${book.title}")
        bookRepository.findById(book.key)
            .orElseThrow { ResourceNotFoundException("Record not found to update") }

        val entity = DozerMapper.parseObject(book, Book::class.java)
        val bookVO = DozerMapper.parseObject(bookRepository.save(entity), BookVO::class.java)
        val withSelRel = linkTo(BookController::class.java).slash(bookVO.key).withSelfRel()
        bookVO.add(withSelRel)

        return bookVO
    }
    fun delete(id: Int) {
        logger.info("deleting one book with id: $id")
        val entity = bookRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Record not found to delete") }
        bookRepository.delete(entity)
    }
}