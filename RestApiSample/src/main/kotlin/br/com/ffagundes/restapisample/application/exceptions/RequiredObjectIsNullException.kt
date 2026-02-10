package br.com.ffagundes.restapisample.application.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

// Podemos indicar o tipo do retorno diretamente
@ResponseStatus(HttpStatus.NOT_FOUND)
class RequiredObjectIsNullException : RuntimeException {
    constructor(): super("The input object is null")
    constructor(exceptionMsg: String?): super(exceptionMsg)
}