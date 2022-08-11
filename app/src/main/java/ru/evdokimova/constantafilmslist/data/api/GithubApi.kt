package ru.evdokimova.constantafilmslist.data.api

import retrofit2.Response
import retrofit2.http.GET
import ru.evdokimova.constantafilmslist.models.FilmsResponse

interface GithubApi {

    @GET("constanta-android-dev/intership-wellcome-task/main/films.json")
    suspend fun getFilms(): Response<FilmsResponse>

}