package br.com.ffagundes.restapisample.integrationtests.vo.wrapper

import com.fasterxml.jackson.annotation.JsonProperty

class WrapperBookVO {
    @JsonProperty("_embedded")
    var embedded: BookEmbeddedVO? = null
}