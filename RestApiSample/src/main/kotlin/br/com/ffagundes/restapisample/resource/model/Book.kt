package br.com.ffagundes.restapisample.resource.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Column
import java.time.LocalDateTime

@Entity
@Table(name = "books")
data class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,

    @Column(name = "author", nullable = false, length = 180)
    var author: String = "",

    @Column(name = "launch_date", nullable = true)
    var launchDate: LocalDateTime? = LocalDateTime.now(),

    @Column(nullable = false)
    var price: Double = 0.0,

    @Column(nullable = false, length = 250)
    var title: String = ""
)
