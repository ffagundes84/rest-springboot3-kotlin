package br.com.ffagundes.restapisample.domain.service

import br.com.ffagundes.restapisample.application.exceptions.ResourceNotFoundException
import br.com.ffagundes.restapisample.resource.model.Person
import br.com.ffagundes.restapisample.resource.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.atomic.AtomicLong
import java.util.logging.Logger
import kotlin.collections.ArrayList

@Service
class PersonService {
    @Autowired
    private lateinit var personRepository: PersonRepository
    private val logger = Logger.getLogger(PersonService::class.java.name)
    private val atomicId: AtomicLong  = AtomicLong()
    fun findById(id: Int): Person {
        logger.info("finding person by id: $id")
        return personRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("No record found for this id.") }
    }

    fun findAll(): List<Person> {
        logger.info("finding all")
        return personRepository.findAll()
    }

    fun create(person: Person) : Person {
        logger.info("saving one person with name: ${person.firstName}")
        return personRepository.save(person)
    }
    fun update(person: Person) {
        logger.info("updating one person with name: ${person.firstName}")
        val entity = personRepository.findById(person.id)
            .orElseThrow { ResourceNotFoundException("Record not found to update") }

        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.address = person.address
        entity.gender = person.gender
    }
    fun delete(id: Int) {
        logger.info("deleting one person with id: $id")
        val entity = personRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Record not found to delete") }
        personRepository.delete(entity)
    }

    companion object {
        fun getPersonMock(id: Long, index: Int) = Person(
            id = id,
            firstName = "Person $index",
            lastName = "Last Name $index",
            address = "Address $index",
            gender = "male"
            )
    }
}