package br.com.ffagundes.restapisample.integrationtests.vo.wrapper

import br.com.ffagundes.restapisample.integrationtests.vo.BookVO
import com.fasterxml.jackson.annotation.JsonProperty

class BookEmbeddedVO {
    @JsonProperty("bookVOList")
    var books: List<BookVO>? = null
}
