package br.com.ffagundes.restapisample.domain.mockito.service

import br.com.ffagundes.restapisample.application.exceptions.RequiredObjectIsNullException
import br.com.ffagundes.restapisample.domain.service.PersonService
import br.com.ffagundes.restapisample.resource.repository.PersonRepository
import br.com.ffagundes.restapisample.unittests.mapper.mocks.MockPerson
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
class PersonServiceTest {
    private lateinit var inputObject: MockPerson
    @InjectMocks
    private lateinit var service: PersonService
    @Mock
    private lateinit var repository: PersonRepository

    @BeforeEach
    fun setUp() {
        inputObject = MockPerson()
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun findById() {
        val id = 1
        val person = inputObject.mockEntity(id)

        `when`(repository.findById(id)).thenReturn(Optional.of(person))

        val result = service.findById(id)

        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</api/person/v1/1>;rel=\"self\""))
        assertEquals("Address Test - 1", result.address)
        assertEquals("First Name Test - 1", result.firstName)
        assertEquals("Last Name Test - 1", result.lastName)
        assertEquals("Male", result.gender)
    }

    @Test
    fun findAll() {
        val sourceList = inputObject.mockEntityList()
        `when`(repository.findAll()).thenReturn(sourceList)

        val persons = service.findAll()
        assertNotNull(persons)
        assertEquals(14, persons.size)

        val personOne = persons[1]
        assertNotNull(personOne)
        assertNotNull(personOne.key)
        assertNotNull(personOne.links)
        assertTrue(personOne.links.toString().contains("</api/person/v1/1>;rel=\"self\""))
        assertEquals("Address Test - 1", personOne.address)
        assertEquals("First Name Test - 1", personOne.firstName)
        assertEquals("Last Name Test - 1", personOne.lastName)
        assertEquals("Male", personOne.gender)

        val personFour = persons[4]
        assertNotNull(personFour)
        assertNotNull(personFour.key)
        assertNotNull(personFour.links)
        assertTrue(personFour.links.toString().contains("</api/person/v1/4>;rel=\"self\""))
        assertEquals("Address Test - 4", personFour.address)
        assertEquals("First Name Test - 4", personFour.firstName)
        assertEquals("Last Name Test - 4", personFour.lastName)
        assertEquals("Female", personFour.gender)
    }

    @Test
    fun create() {
        val id = 1
        val entity = inputObject.mockEntity(id)
        val persisted = entity.copy()

        `when`(repository.save(entity)).thenReturn(persisted)

        val personVO = inputObject.mockVO(id)
        val result = service.create(personVO)

        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</api/person/v1/1>;rel=\"self\""))
        assertEquals("Address Test - 1", result.address)
        assertEquals("First Name Test - 1", result.firstName)
        assertEquals("Last Name Test - 1", result.lastName)
        assertEquals("Male", result.gender)
    }

    @Test
    fun createV2() {
    }

    @Test
    fun update() {
        val id = 1
        val entity = inputObject.mockEntity(id)
        val persisted = entity.copy()

        `when`(repository.findById(id)).thenReturn(Optional.of(entity))
        `when`(repository.save(entity)).thenReturn(persisted)

        val personVO = inputObject.mockVO(id)
        val result = service.update(personVO)

        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</api/person/v1/1>;rel=\"self\""))
        assertEquals("Address Test - 1", result.address)
        assertEquals("First Name Test - 1", result.firstName)
        assertEquals("Last Name Test - 1", result.lastName)
        assertEquals("Male", result.gender)
    }

    @Test
    fun delete() {
        val id = 1
        val person = inputObject.mockEntity(id)

        `when`(repository.findById(id)).thenReturn(Optional.of(person))

        service.delete(id)
    }

    @Test
    fun createWithNullPerson() {
        val exception: Exception = assertThrows (
            RequiredObjectIsNullException::class.java
        ) { service.create(null) }

        assertTrue(exception.message.toString().contains("The input object is null"))
    }

    @Test
    fun updateWithNullPerson() {
        val exception: Exception = assertThrows (
            RequiredObjectIsNullException::class.java
        ) { service.update(null) }

        assertTrue(exception.message.toString().contains("The input object is null"))
    }
}