package br.com.ffagundes.restapisample.integrationtests.vo.wrapper

import com.fasterxml.jackson.annotation.JsonProperty

class WrapperPersonVO {
    @JsonProperty("_embedded")
    var embedded: PersonEmbeddedVO? = null
}