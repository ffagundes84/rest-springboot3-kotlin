package br.com.ffagundes.restapisample.resource.repository

import br.com.ffagundes.restapisample.resource.model.Person
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface PersonRepository : JpaRepository<Person, Int?> {
    @Modifying
    @Transactional
    @Query("UPDATE Person SET enabled = false WHERE id =:id")
    fun disablePerson(@Param("id") id: Int?)

    @Query("SELECT p FROM Person p WHERE p.firstName LIKE LOWER(CONCAT('%',:firstName,'%'))")
    fun findByName(@Param("firstName") firstName: String, pageable: Pageable): Page<Person>
}