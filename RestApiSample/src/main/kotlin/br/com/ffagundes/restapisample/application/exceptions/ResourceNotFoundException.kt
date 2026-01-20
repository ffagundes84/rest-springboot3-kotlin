package br.com.ffagundes.restapisample.application.exceptions

// Podemos indicar o tipo do retorno diretamente
// @ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException(exception: String) : RuntimeException(exception) {
}