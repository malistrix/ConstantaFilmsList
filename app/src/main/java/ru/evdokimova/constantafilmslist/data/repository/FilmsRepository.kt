package ru.evdokimova.constantafilmslist.data.repository

import android.util.Log
import ru.evdokimova.constantafilmslist.data.api.GithubApi
import ru.evdokimova.constantafilmslist.models.Film
import ru.evdokimova.constantafilmslist.utils.Resource
import javax.inject.Inject

private const val TAG = "FilmsRepository "

class FilmsRepository @Inject constructor(private val imageApi: GithubApi) {

    suspend fun getResourceFilms(): Resource<List<Film>> = try {
        val responseFilms = imageApi.getFilms()
        if (responseFilms.isSuccessful) {
            Resource.Success(responseFilms.body()!!.items)
        } else {
            Resource.Error("ResponseFilms is not successful, code ${responseFilms.message()}")
        }
    } catch (e: Exception) {
        Log.e(TAG, "${e.message}")
        e.printStackTrace()
        Resource.Error("ResponseFilms is not successful, ${e.message}")
    }
}