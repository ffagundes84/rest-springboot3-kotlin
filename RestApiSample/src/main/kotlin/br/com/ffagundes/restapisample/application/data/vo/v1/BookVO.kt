package br.com.ffagundes.restapisample.application.data.vo.v1
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.github.dozermapper.core.Mapping
import org.springframework.hateoas.RepresentationModel
import java.time.LocalDateTime

@JsonPropertyOrder("id", "author", "launchDate", "price", "title")
data class BookVO(
    @Mapping("id")
    @field:JsonProperty("id")
    var key: Int = 0,
    var author: String = "",
    var launchDate: LocalDateTime = LocalDateTime.now(),
    var price: Double = 0.0,
    var title: String = ""
) : RepresentationModel<BookVO>()
