package com.example.film

import android.media.Image
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView

class viewholder (item : View) : RecyclerView.ViewHolder(item){
    //val addbutton : Button
    val poster : ImageView
    val isim : TextView
    val fiyat : TextView
    val puan : TextView
    val imdb : ImageView
    val agerange : ImageView

    init {
        poster = item.findViewById(R.id.poster)

        isim = item.findViewById(R.id.isimdesign3)

        fiyat = item.findViewById(R.id.fiyat)

        puan = item.findViewById(R.id.imdbpuani)

        imdb = item.findViewById(R.id.imageView2)

        agerange = item.findViewById(R.id.pluseighteen)

        //addbutton = item.findViewById(R.id.eklebuton)

    }
}

class benzerviewh (item: View) : RecyclerView.ViewHolder(item){
    val poster : ImageView
    val isim : TextView
    val puan : TextView
    val imdb : ImageView
    val agerange : ImageView
    val fiyat : TextView
    init{
        poster = item.findViewById(R.id.poster)

        isim = item.findViewById(R.id.isimdesign3)

        puan = item.findViewById(R.id.imdbpuani)

        imdb = item.findViewById(R.id.imageView2)

        agerange = item.findViewById(R.id.pluseighteen)

        fiyat = item.findViewById(R.id.fiyattext)
    }
}

class siparisviewh (item : View) : RecyclerView.ViewHolder(item){
    val fiyatsip : TextView
    val postersip : ImageView
    val isimsip : TextView
    init{
        fiyatsip = item.findViewById(R.id.siparisfiyat)
        postersip = item.findViewById(R.id.siparisposter)
        isimsip = item.findViewById(R.id.siparisisim)
    }
}
class arama_veiewh(item: View) : RecyclerView.ViewHolder(item){
    val posterarama : ImageView
    val isimarama : TextView
    val aramayil : TextView
    val fiyatarama : TextView
    init {
        posterarama = item.findViewById<ImageView>(R.id.aramaposter)
        isimarama = item.findViewById<TextView>(R.id.isimarama)
        aramayil = item.findViewById<TextView>(R.id.yilarama)
        fiyatarama = item.findViewById<TextView>(R.id.fiyatarama)
    }
}

class viewhIzlemeyeDevamEdin(item : View) : RecyclerView.ViewHolder(item){
    val isim : TextView
    val devambuton : AppCompatButton
    val posterdevam : ImageView
    init {
        isim = item.findViewById(R.id.isimdesign3)
        devambuton = item.findViewById(R.id.devambuton)
        posterdevam = item.findViewById(R.id.posterdevam)
    }
}

class viewhKutuphane(item : View) : RecyclerView.ViewHolder(item){
    val poster : ImageView
    val isim : TextView
    init {
        isim = item.findViewById(R.id.isimdesign4)
        poster = item.findViewById(R.id.posterkutuphane)
    }
}