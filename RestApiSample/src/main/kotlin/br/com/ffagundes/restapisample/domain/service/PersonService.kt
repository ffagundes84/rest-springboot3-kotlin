package br.com.ffagundes.restapisample.domain.service

import br.com.ffagundes.restapisample.resource.Person
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong
import java.util.logging.Logger

@Service
class PersonService {
    private val logger = Logger.getLogger(PersonService::class.java.name)
    private val atomicId: AtomicLong  = AtomicLong()
    fun findById(id: Int): Person {
        logger.info("finding person by id: $id")
        return getPersonMock(atomicId.incrementAndGet(), id)
    }

    fun findAll(): List<Person> {
        logger.info("finding all")

        val persons: MutableList<Person> = ArrayList()

        for (i in 0..7){
            persons.add(getPersonMock(atomicId.incrementAndGet(), i))
        }

        return persons
    }

    fun create(person: Person) = person
    fun update(person: Person) = person
    fun delete(id: Int) {}

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