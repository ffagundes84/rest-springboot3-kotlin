package br.com.ffagundes.restapisample.application.controller

import br.com.ffagundes.restapisample.application.data.vo.v1.PersonVO
import br.com.ffagundes.restapisample.application.data.vo.v2.PersonVO as PersonVOV2
import br.com.ffagundes.restapisample.domain.service.PersonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/person")
class PersonController {
    @Autowired
    private lateinit var service: PersonService

    @GetMapping(value = ["/{id}"],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findById(@PathVariable("id") id: Int) : PersonVO {
        return service.findById(id)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(): List<PersonVO> {
        return service.findAll()
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody person: PersonVO): PersonVO {
        return service.create(person)
    }

    @PostMapping(value = ["/v2"], consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createV2(@RequestBody person: PersonVOV2): PersonVOV2 {
        return service.createV2(person)
    }

    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun update(@RequestBody person: PersonVO): PersonVO {
        return service.update(person)
    }

    @DeleteMapping(value = ["/{id}"],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun delete(@PathVariable("id") id: Int) : ResponseEntity<Any> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }
}