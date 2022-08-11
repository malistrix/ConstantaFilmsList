package ru.evdokimova.constantafilmslist.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.evdokimova.constantafilmslist.R
import ru.evdokimova.constantafilmslist.databinding.ItemFilmBinding
import ru.evdokimova.constantafilmslist.models.Film

class FilmsAdapter :
    RecyclerView.Adapter<FilmsAdapter.FilmViewHolder>() {

    private var films: List<Film> = listOf()

    fun submitData(listFilms: List<Film>) {
        films = listFilms
    }

    inner class FilmViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemFilmBinding.bind(view)

        fun bind(film: Film) = with(binding) {
            val header = "${film.title} (${film.releaseYear})"
            filmHeader.text = header
            filmDirectorName.text = convertDirectorName(film.directorName)
            filmActors.text = film.actors.joinToString {it.actorName}
        }

        private fun convertDirectorName(name: String): String{
            val list = name.split("\\s+".toRegex())
            var result = ""
            if(list.isNotEmpty()){
                result = "${list.last()} "
                val lastInd = list.lastIndex
                for( i in 0 until lastInd){
                    result += "${list[i].substring(0, 1)}."
                }
            }
            return result
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        return FilmViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_film, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(films[position])
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(films[position]) }
        }
    }

    override fun getItemCount() = films.size

    private var onItemClickListener: ((Film) -> Unit)? = null

    fun setOnItemClickListener(listener: (Film) -> Unit) {
        onItemClickListener = listener
    }
}