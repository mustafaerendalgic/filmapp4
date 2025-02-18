package com.example.film

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
//import com.google.android.exoplayer2.ui.PlayerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class detayekran : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets}

        val db = filmdatabase.getthetable(this)

        val screenwidth = resources.displayMetrics.widthPixels
        val density = resources.displayMetrics.density
        val cardwidth = (density * 180).toInt()
        val space = (screenwidth - 2*cardwidth)/3

        val fiyat = findViewById<TextView>(R.id.fiyat)
        val eklebutn = findViewById<AppCompatButton>(R.id.buttondetay)
        val sahiplik = findViewById<ConstraintLayout>(R.id.sahiplik)
        val posterblurry = findViewById<ImageView>(R.id.blurry)
        val posterdetay = findViewById<ImageView>(R.id.posterdetay)
        val yil = findViewById<TextView>(R.id.yil)
        val puan = findViewById<TextView>(R.id.detaypuan)
        val uzunluk = findViewById<TextView>(R.id.uzunluk)
        val aciklama = findViewById<TextView>(R.id.detayaciklama)
        val isim = findViewById<TextView>(R.id.detayFilmismi)
        val backbutton = findViewById<ImageView>(R.id.backbutton)
        val tur = findViewById<TextView>(R.id.tur)
        val sepeteekledetay = findViewById<Button>(R.id.buttondetay)
        val id = intent.getIntExtra("index", 0)

        lifecycleScope.launch {
            val item = withContext(Dispatchers.IO){ db.filmDao().getmoviebyid(id)}
            val fetchlist = mutableSetOf<film>()
            withContext(Dispatchers.IO) {
                val turlist = item.tur.split(",").toList()
                turlist.forEach {
                    val fetch = db.filmDao().getmoviebygenreaslist(it, item.id)
                    if (fetchlist.size < 10) {
                        fetchlist.addAll(fetch)
                    }
                }
            }

            withContext(Dispatchers.Main){

                fetchlist.remove(item)

                fiyat.text = ("${item.fiyat} ₺")

                yil.text = "(${item.yapımyil})"

                puan.text = item.puan.toString()

                uzunluk.text = item.uzunluk

                aciklama.text = item.contentDesc

                isim.text = item.isim

                tur.text = item.tur

                if(item.isbought){
                    fiyat.visibility = View.INVISIBLE
                    eklebutn.visibility = View.INVISIBLE
                    sahiplik.visibility = View.VISIBLE
                }
                else{
                    fiyat.visibility = View.VISIBLE
                    eklebutn.visibility = View.VISIBLE
                    sahiplik.visibility = View.INVISIBLE
                }

                Glide.with(this@detayekran).load(item.posterurl).into(posterblurry)

                Glide.with(this@detayekran)
                    .load(item.posterurl)
                    .into(posterdetay)

                sepeteekledetay.setOnClickListener {
                    if(item != null && !orderlist.contains(item)){
                        orderlist.add(item)
                        if(orderlist.contains(item))
                            Toast.makeText(this@detayekran, "Ürün sepete eklendi", Toast.LENGTH_SHORT).show()
                        else
                            Toast.makeText(this@detayekran, "Ürün sepete eklenirken bir hata oluştu", Toast.LENGTH_SHORT).show()
                    }
                    else
                        Toast.makeText(this@detayekran, "Ürün zaten sepete eklendi", Toast.LENGTH_SHORT).show()
                }

                val benzer = findViewById<RecyclerView>(R.id.benzerrv)
                val laym = LinearLayoutManager(this@detayekran, LinearLayoutManager.HORIZONTAL, false)
                val adapterbenz = Adapterbenzer()
                adapterbenz.submitList(fetchlist.toList())
                benzer.adapter = adapterbenz
                benzer.addItemDecoration(customDesignCodes(fetchlist.size, space, true))
                benzer.layoutManager = laym
                val snaphelper = LeftSnapHelper(space)
                snaphelper.attachToRecyclerView(benzer)

            }
        }

        backbutton.setOnClickListener {
            finish()
        }

    }
}