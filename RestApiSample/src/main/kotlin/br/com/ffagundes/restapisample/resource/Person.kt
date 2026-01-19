package br.com.ffagundes.restapisample.resource

import java.util.concurrent.atomic.AtomicLong

data class Person(
    var id: Long = createId(),
    var firstName: String,
    var lastName: String,
    var address: String,
    var gender: String
) {
    companion object {
        fun createId(): Long {
            val id = AtomicLong()
            return id.incrementAndGet()
        }
    }
}
