package br.com.ffagundes.restapisample.integrationtests.vo.wrapper

import br.com.ffagundes.restapisample.integrationtests.vo.PersonVO
import com.fasterxml.jackson.annotation.JsonProperty

class PersonEmbeddedVO {
    @JsonProperty("personVOList")
    var persons: List<PersonVO>? = null
}
