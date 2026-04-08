package br.com.ffagundes.restapisample.domain.service

import br.com.ffagundes.restapisample.application.controller.PersonController
import br.com.ffagundes.restapisample.application.data.vo.v1.PersonVO
import br.com.ffagundes.restapisample.application.exceptions.RequiredObjectIsNullException
import br.com.ffagundes.restapisample.application.data.vo.v2.PersonVO as PersonVOV2
import br.com.ffagundes.restapisample.application.exceptions.ResourceNotFoundException
import br.com.ffagundes.restapisample.application.mapper.DozerMapper
import br.com.ffagundes.restapisample.application.mapper.custom.PersonMapper
import br.com.ffagundes.restapisample.resource.model.Person
import br.com.ffagundes.restapisample.resource.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class PersonService {
    @Autowired
    private lateinit var personRepository: PersonRepository
    @Autowired
    private lateinit var pagedResourceAssembler: PagedResourcesAssembler<PersonVO>

    private val logger = Logger.getLogger(PersonService::class.java.name)
    fun findById(id: Int): PersonVO {
        logger.info("finding person by id: $id")
        val person = personRepository.findById(id)
            .orElseThrow {ResourceNotFoundException("No record found for id $id")}
        val personVO = DozerMapper.parseObject(person, PersonVO::class.java)
        val withSelRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelRel)
        return personVO
    }

    // fun findAll(pageable: Pageable): Page<PersonVO> { // Assim não retorna link para outras páginas da pesquisa 
    fun findAll(pageable: Pageable): PagedModel<EntityModel<PersonVO>> {
        logger.info("finding all")
        val persons = personRepository.findAll(pageable)
        val personsVO = persons.map { person -> DozerMapper.parseObject(person, PersonVO::class.java) }
        personsVO.map { it.add(linkTo(PersonController::class.java).slash(it.key).withSelfRel()) }
        return pagedResourceAssembler.toModel(personsVO)
    }

    fun findByName(firstName: String, pageable: Pageable): PagedModel<EntityModel<PersonVO>> {
        logger.info("finding by name: $firstName")
        val persons = personRepository.findByName(firstName, pageable)
        val personsVO = persons.map { person -> DozerMapper.parseObject(person, PersonVO::class.java) }
        personsVO.map { it.add(linkTo(PersonController::class.java).slash(it.key).withSelfRel()) }
        return pagedResourceAssembler.toModel(personsVO)
    }

    fun create(person: PersonVO?) : PersonVO {
        if (person == null) throw RequiredObjectIsNullException()
        logger.info("saving one person with name: ${person.firstName}")
        val entity: Person = DozerMapper.parseObject(person, Person::class.java)
        val personVO: PersonVO = DozerMapper.parseObject(personRepository.save(entity),PersonVO::class.java)
        val withSelRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelRel)
        return personVO
    }

    fun createV2(personVO: PersonVOV2) : PersonVOV2 {
        logger.info("saving one person with name: ${personVO.firstName} - v2")
        logger.info("object: $personVO - v2")
        return PersonMapper.entityToVO(personRepository.save(PersonMapper.voToEntity(personVO)))
    }
    fun update(person: PersonVO?) : PersonVO {
        if (person == null) throw RequiredObjectIsNullException()
        logger.info("updating one person with name: ${person.firstName}")
        logger.info("object personVO: $person")
        personRepository.findById(person.key)
            .orElseThrow { ResourceNotFoundException("Record not found to update") }

        val entity = DozerMapper.parseObject(person, Person::class.java)
        val personVO = DozerMapper.parseObject(personRepository.save(entity), PersonVO::class.java)
        val withSelRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelRel)

        return personVO
    }


    fun disablePerson(id: Int): PersonVO {
        logger.info("disabling person by id: $id")
        personRepository.disablePerson(id)
        val person = personRepository.findById(id)
            .orElseThrow {ResourceNotFoundException("No record found for id $id")}
        val personVO = DozerMapper.parseObject(person, PersonVO::class.java)
        val withSelRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelRel)
        return personVO
    }
    fun delete(id: Int) {
        logger.info("deleting one person with id: $id")
        val entity = personRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Record not found to delete") }
        personRepository.delete(entity)
    }
}