package br.com.ffagundes.restapisample.application.controller

import br.com.ffagundes.restapisample.application.extensions.StringExtension.isNumber
import br.com.ffagundes.restapisample.domain.MathSerivce
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MathController {
    val mathService = MathSerivce()
    @RequestMapping(value = ["sum/{numberOne}/{numberTwo}"])
    fun sum(
        @PathVariable("numberOne") numberOne: String,
        @PathVariable("numberTwo") numberTwo: String
    ): Double {
        if (!numberOne.isNumber() || !numberTwo.isNumber())
            throw UnsupportedOperationException("param is not a number")
        return mathService.sum(numberOne.toDouble(), numberTwo.toDouble())
    }

    @RequestMapping(value = ["subtraction/{numberOne}/{numberTwo}"])
    fun subtraction(
        @PathVariable("numberOne") numberOne: String,
        @PathVariable("numberTwo") numberTwo: String
    ): Double {
        if (!numberOne.isNumber() || !numberTwo.isNumber())
            throw UnsupportedOperationException("param is not a number")
        return mathService.subtraction(numberOne.toDouble(), numberTwo.toDouble())
    }
}