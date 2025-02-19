package com.example.film

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch




class View_model (val filmdao: FilmDao) : ViewModel(){

    val boughtmovies : LiveData<List<film>> = filmdao.getbought()
    val whatsnewlist : LiveData<List<film>> = filmdao.getTheWhatsNewList()

    val korkulist: LiveData<List<film>> = filmdao.getmoviebygenre("Korku")
    val animelist: LiveData<List<film>> = filmdao.getmoviebygenre("Anime")
    val fantastiklist: LiveData<List<film>> = filmdao.getmoviebygenre("Fantastik")
    val suçlist: LiveData<List<film>> = filmdao.getmoviebygenre("Suç")
    val serikatillist: LiveData<List<film>> = filmdao.getmoviebygenre("Seri Katil")
    val bilimkurgulist: LiveData<List<film>> = filmdao.getmoviebygenre("Bilim Kurgu")

    val films = listOf(
        film(
            drakulaPoster,
            "Drakula (1931)",
            7.4f,
            50.0f,
            "1 Saat 15 Dk.",
            drakulaDesc,
            "1931",
            "Vampir, Korku"
        ),
        film(
            othersPoster,
            "The Others",
            7.6f,
            50.0f,
            "1 Saat 41 Dk.",
            othersDesc,
            "2001",
            "Doğaüstü Korku, Korku"
        ),
        film(
            letTheRightOneInPoster,
            "Let The Right One In",
            7.8f,
            50.0f,
            "1 Saat 54 Dk.",
            letTheRightOneInDesc,
            "2008",
            "Vampir, Korku"
        ),
        film(
            angstPoster,
            "Angst (1983)",
            7.3f,
            40f,
            "1 Saat 27 Dk.",
            angstDesc,
            "1983",
            "Seri Katil Belgeseli, Korku",
            false,
            true
        ),
        film(
            theFellowShipOfTheRingPoster,
            "Yüzüklerin Efendisi: Yüzük Kardeşliği",
            8.9f,
            50.0f,
            "2 Saat 58 Dk.",
            theFellowShipOfTheRingDesc,
            "2001",
            "Fantastik, Macera"
        ),
        film(
            theNightOfTheHunterPoster,
            "The Night Of The Hunter",
            8.0f,
            40.0f,
            "1 Saat 32 Dk.",
            theNightOfTheHunterDesc,
            "1955",
            "Suç, Gerilim"
        ),
        film(
            whatEverHappenedToBabyJanePoster,
            "Küçük Bebeğe Ne Oldu?",
            8.0f,
            50.0f,
            "2 Saat 14 Dk.",
            whatEverHappenedToBabyJaneDesc,
            "1962",
            "Suç, Gerilim"
        ),
        film(
            spiritedAwayPoster,
            "Spirited Away",
            8.6f,
            50.0f,
            "2 Saat 4 Dk.",
            spiritedAwayDesc,
            "2001",
            "Anime, Macera, Fantastik"
        ),
        film(
            dunePartOnePoster,
            "Dune: Birinci Bölüm",
            8.0f,
            50.0f,
            "2 Saat 35 Dk.",
            dunePartOneDesc,
            "2021",
            "Post Apokaliptik, Bilim Kurgu"
        ),
        film(
            manBitesDogPoster,
            "C'est Arrive Pres de Chez Vous",
            7.4f,
            40.0f,
            "1 Saat 35 Dk.",
            manBitesDogDesc,
            "1992",
            "Seri Katil Belgeseli, Korku",
            false,
            true
        ),
        film(
            graveOfTheFirefliesPoster,
            "Grave Of The Fireflies",
            8.5f,
            50.0f,
            "1 Saat 28 Dk.",
            graveOfTheFirefliesDesc,
            "1988",
            "Anime, Dram, Savaş"
        ),
        film(
            madMaxPoster,
            "Mad Max",
            8.1f,
            50.0f,
            "2 Saat",
            madMaxDesc,
            "2015",
            "Post Apokaliptik, Bilim Kurgu, Dram"
        ),
        film(
            elOrfanatoPoster,
            "El Orfanato (Yetimhane)",
            7.4f,
            50.0f,
            "1 Saat 45 Dk.",
            elOrfanatoDesc,
            "2007",
            "Doğaüstü Korku, Korku"
        ),
        film(
            henryPortraitOfASerialKillerPoster,
            "Henry: Portrait Of A Serial Killer",
            7.0f,
            40.0f,
            "1 Saat 23 Dk.",
            henryPortraitOfASerialKillerDesc,
            "1986",
            "Seri Katil Belgeseli, Belgesel, Korku",
            false,
            true
        ),
        film(
            gobletOfFirePoster,
            "Harry Potter: Goblet Of Fire",
            7.7f,
            50.0f,
            "2 Saat 37 Dk.",
            gobletOfFireDesc,
            "2005",
            "Fantastik, Macera"
        )
    )

    fun initializefilmlist() {

        viewModelScope.launch {

            val maxid = filmdao.getmaxid() ?: 0

            if (maxid == 0) {
                filmdao.filmlistesiekle(films)
            } else {
                val newfilms = films.filter { it -> it.id > maxid }
                if (!newfilms.isEmpty())
                    filmdao.filmlistesiekle(ArrayList(newfilms))
            }

        }
    }
}

class Viewmodelfactory(private val filmdao : FilmDao) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(View_model::class.java))
            return View_model(filmdao) as T

        throw IllegalArgumentException("Invalid Class of a View Model")
    }
}