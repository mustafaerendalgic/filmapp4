package com.example.film

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class siparis : AppCompatActivity() {

    lateinit var siparisadapter : Adaptersiparis

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = filmdatabase.getthetable(this)
        val satinalbuton = findViewById<AppCompatButton>(R.id.satınal)

        satinalbuton.setOnClickListener {
            if(!orderlist.isEmpty()) {
                val alert = AlertDialog.Builder(this@siparis)
                alert.setMessage("Ürünleri satın almak istediğinize emin misiniz?")
                alert.setPositiveButton("Evet", { dialog, which ->
                    lifecycleScope.launch {
                        val ids = orderlist.map { it.id }
                        withContext(Dispatchers.IO) {
                            db.filmDao().updatebought(ids)
                        }
                        withContext(Dispatchers.Main) {
                            orderlist.clear()
                            siparisadapter.notifyDataSetChanged()
                            Toast.makeText(this@siparis, "İşlem Başarılı", Toast.LENGTH_LONG)
                                .show()
                            finish()
                        }
                    }
                }
                )
                alert.setNegativeButton("Hayır", null)
                alert.show()
            }
            else{
                Toast.makeText(this, "Sepet boş", Toast.LENGTH_SHORT).show()
            }
        }

        val emptyimage = findViewById<ImageView>(R.id.emptyimage)
        emptyimage.visibility = View.INVISIBLE
        val toplam = findViewById<TextView>(R.id.textView4)
        var toplamfiyat = 0f

        if(!orderlist.isEmpty()){
            for(film in orderlist){
                toplamfiyat += film.fiyat
            }
        }
        else{
            emptyimage.visibility = View.VISIBLE
        }
        toplam.text = "${toplamfiyat} TL"

        siparisadapter = Adaptersiparis()
        siparisadapter.submitList(orderlist)

        val rvsiparis = findViewById<RecyclerView>(R.id.sipraisrv)
        rvsiparis.adapter = siparisadapter

        val laymsip = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvsiparis.layoutManager = laymsip
        rvsiparis.addItemDecoration(customDesignCodes(1, 40, true))

        val back = findViewById<ImageView>(R.id.imageView5)
        back.setOnClickListener {
            finish()
        }
    }
    fun iptalet() {
        val builder = AlertDialog.Builder(this)
        if(!orderlist.isEmpty()){
            builder.setMessage("Siparişi iptal etmek istediğinize emin misiniz?")
            builder.setPositiveButton("Evet", { dialog, which ->
                orderlist.clear()
                siparisadapter.notifyDataSetChanged()
                finish()
            })
            builder.setNegativeButton("Hayır", {dialog, which ->})
            val dialog = builder.create()
            dialog.show()
        }

    }
}