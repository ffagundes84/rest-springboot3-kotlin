package br.com.ffagundes.restapisample.application.extensions

object StringExtension {
    fun String.isNumber() : Boolean {
        return this.toDoubleOrNull() != null
    }
}