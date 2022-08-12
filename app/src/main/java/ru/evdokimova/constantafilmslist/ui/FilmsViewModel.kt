package ru.evdokimova.constantafilmslist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.evdokimova.constantafilmslist.data.repository.FilmsRepository
import ru.evdokimova.constantafilmslist.models.Film
import ru.evdokimova.constantafilmslist.utils.Resource
import javax.inject.Inject

@HiltViewModel
class FilmsViewModel @Inject constructor(
    private val repository: FilmsRepository
) : ViewModel() {

    private val _filmsLiveData: MutableLiveData<Resource<List<Film>>> = MutableLiveData()
    val filmsLiveData: LiveData<Resource<List<Film>>>
        get() = _filmsLiveData

    init {
        updateFilmsLiveData()
    }

    fun updateFilmsLiveData() {
        viewModelScope.launch {
            _filmsLiveData.postValue(Resource.Loading())

            val filmsRes = repository.getResourceFilms()

            if (filmsRes is Resource.Success) {
                val films = mutableListOf<Film>()
                filmsRes.data!!.sortedBy { it.releaseYear }.forEach {
                    films.add(
                        Film(
                            it.title,
                            it.directorName,
                            it.releaseYear,
                            it.actors.distinct()
                        )
                    )
                }
                _filmsLiveData.postValue(Resource.Success(films))
            } else {
                _filmsLiveData.postValue(filmsRes)
            }
        }
    }
}