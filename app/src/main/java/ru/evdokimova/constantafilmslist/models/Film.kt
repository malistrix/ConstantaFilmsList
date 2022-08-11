package ru.evdokimova.constantafilmslist.models

data class Film(
    val title: String,
    val directorName: String,
    val releaseYear: Int,
    //val actors: Set<Actor>  - это было бы слишком легко, не правда ли? =)
    var actors: List<Actor>

)