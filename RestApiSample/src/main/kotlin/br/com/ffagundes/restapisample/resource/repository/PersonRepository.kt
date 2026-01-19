package br.com.ffagundes.restapisample.resource.repository

import br.com.ffagundes.restapisample.resource.model.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository : JpaRepository<Person, Int?>{
}