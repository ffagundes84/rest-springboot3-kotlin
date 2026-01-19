package br.com.ffagundes.restapisample.application.controller

import br.com.ffagundes.restapisample.domain.service.PersonService
import br.com.ffagundes.restapisample.resource.model.Person
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/person")
class PersonController {
    @Autowired
    private lateinit var service: PersonService

    @RequestMapping(value = ["/{id}"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findById(@PathVariable("id") id: Int) : Person {
        return service.findById(id)
    }

    @RequestMapping(/*value = ["/findAll"],*/
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(): List<Person> {
        return service.findAll()
    }

    @RequestMapping(/*value = ["/create"],*/
        method = [RequestMethod.POST],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody person: Person): Person {
        return service.create(person)
    }

    @RequestMapping(/*value = ["/create"],*/
        method = [RequestMethod.PUT],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun update(@RequestBody person: Person): Person {
        return service.update(person)
    }

    @RequestMapping(value = ["/{id}"],
        method = [RequestMethod.DELETE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun delete(@PathVariable("id") id: Int) {
        return service.delete(id)
    }
}