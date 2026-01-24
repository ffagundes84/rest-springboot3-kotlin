package br.com.ffagundes.restapisample.domain.service

import br.com.ffagundes.restapisample.application.data.vo.v1.PersonVO
import br.com.ffagundes.restapisample.application.data.vo.v2.PersonVO as PersonVOV2
import br.com.ffagundes.restapisample.application.exceptions.ResourceNotFoundException
import br.com.ffagundes.restapisample.application.mapper.ModelMapper
import br.com.ffagundes.restapisample.application.mapper.custom.PersonMapper
import br.com.ffagundes.restapisample.resource.model.Person
import br.com.ffagundes.restapisample.resource.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class PersonService {
    @Autowired
    private lateinit var personRepository: PersonRepository
    private val logger = Logger.getLogger(PersonService::class.java.name)
    fun findById(id: Int): PersonVO {
        logger.info("finding person by id: $id")
        val person = personRepository.findById(id)
            .orElseThrow {ResourceNotFoundException("No record found for id $id")}
        return ModelMapper.parseObject(person, PersonVO::class.java)
    }

    fun findAll(): List<PersonVO> {
        logger.info("finding all")
        return ModelMapper.parseListObjects(personRepository.findAll(), PersonVO::class.java)
    }

    fun create(personVO: PersonVO) : PersonVO {
        logger.info("saving one person with name: ${personVO.firstName}")
        return ModelMapper.parseObject(
            personRepository.save(ModelMapper.parseObject(personVO, Person::class.java)),
            PersonVO::class.java
        )
    }
    fun createV2(personVO: PersonVOV2) : PersonVOV2 {
        logger.info("saving one person with name: ${personVO.firstName} - v2")
        logger.info("object: $personVO - v2")
        return PersonMapper.entityToVO(personRepository.save(PersonMapper.voToEntity(personVO)))
    }
    fun update(personVO: PersonVO) : PersonVO {
        logger.info("updating one person with name: ${personVO.firstName}")
        logger.info("object personVO: $personVO")
        personRepository.findById(personVO.id)
            .orElseThrow { ResourceNotFoundException("Record not found to update") }

        return ModelMapper.parseObject(
            personRepository.save(ModelMapper.parseObject(personVO, Person::class.java)),
            PersonVO::class.java
        )
    }
    fun delete(id: Int) {
        logger.info("deleting one person with id: $id")
        val entity = personRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Record not found to delete") }
        personRepository.delete(entity)
    }
}