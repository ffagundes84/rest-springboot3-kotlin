package br.com.ffagundes.restapisample.domain.mockito.service

import br.com.ffagundes.restapisample.application.exceptions.RequiredObjectIsNullException
import br.com.ffagundes.restapisample.domain.service.BookService
import br.com.ffagundes.restapisample.resource.repository.BookRepository
import br.com.ffagundes.restapisample.unittests.mapper.mocks.MockBook
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class BookServiceTest {
    private lateinit var inputObject: MockBook
    @InjectMocks
    private lateinit var service: BookService
    @Mock
    private lateinit var repository: BookRepository

    @BeforeEach
    fun setUp() {
        inputObject = MockBook()
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun findById() {
        val id = 1
        val date = Date()
        val book = inputObject.mockEntity(id)

        `when`(repository.findById(id)).thenReturn(Optional.of(book))

        val result = service.findById(id)

        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</api/book/v1/1>;rel=\"self\""))
        assertEquals("Author 1", result.author)
        assertEquals("Title 1", result.title)
        assertEquals(date, result.launchDate)
        assertEquals("1.0".toDouble(), result.price)
        assertEquals("1".toInt(), result.key)
    }

    @Test
    fun create() {
        val id = 1
        val date = Date()
        val entity = inputObject.mockEntity(id, date)
        val persisted = entity.copy()

        `when`(repository.save(entity)).thenReturn(persisted)

        val bookVO = inputObject.mockVO(id, date)
        val result = service.create(bookVO)

        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</api/book/v1/1>;rel=\"self\""))
        assertEquals("Author 1", result.author)
        assertEquals("Title 1", result.title)
        assertEquals(date, result.launchDate)
        assertEquals("1.0".toDouble(), result.price)
        assertEquals("1".toInt(), result.key)
    }

    @Test
    fun update() {
        val id = 1
        val date = Date()
        val entity = inputObject.mockEntity(id, date)
        val persisted = entity.copy()

        `when`(repository.findById(id)).thenReturn(Optional.of(entity))
        `when`(repository.save(entity)).thenReturn(persisted)

        val bookVO = inputObject.mockVO(id, date)
        val result = service.update(bookVO)

        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</api/book/v1/1>;rel=\"self\""))
        assertEquals("Author 1", result.author)
        assertEquals("Title 1", result.title)
        assertEquals(date, result.launchDate)
        assertEquals("1.0".toDouble(), result.price)
        assertEquals("1".toInt(), result.key)
    }

    @Test
    fun delete() {
        val id = 1
        val book = inputObject.mockEntity(id)

        `when`(repository.findById(id)).thenReturn(Optional.of(book))

        service.delete(id)
    }

    @Test
    fun createWithNullBook() {
        val exception: Exception = assertThrows (
            RequiredObjectIsNullException::class.java
        ) { service.create(null) }

        assertTrue(exception.message.toString().contains("The input object is null"))
    }

    @Test
    fun updateWithNullBook() {
        val exception: Exception = assertThrows (
            RequiredObjectIsNullException::class.java
        ) { service.update(null) }

        assertTrue(exception.message.toString().contains("The input object is null"))
    }
}