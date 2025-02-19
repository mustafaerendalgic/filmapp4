package com.example.film

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide


class Adaptermain : ListAdapter<film, viewholder>(FilmDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder{
        val v = LayoutInflater.from(parent.context).inflate(R.layout.design, parent, false)
        return viewholder(v)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {

        val item = getItem(position)

        val formattedPrice = "%.2f ₺".format(item.fiyat)

        holder.isim.text = item.isim

        holder.imdb.setImageResource(R.drawable.imdb)

        holder.fiyat.text = formattedPrice

        holder.puan.text = item.puan.toString()

        if (item.agerange) {
            holder.agerange.visibility = View.VISIBLE
        } else {
            holder.agerange.visibility = View.INVISIBLE
        }

        Glide.with(holder.itemView.context)
            .load(item.posterurl)
            .error(R.drawable.renkgecis2)
            .into(holder.poster)


        holder.itemView.setOnClickListener {
            val detailintent = Intent(holder.itemView.context, detayekran::class.java)
            detailintent.putExtra("index", item.id)
            //detayekranitem = item.id
            holder.itemView.context.startActivity(detailintent)
        }

    }

}

class FilmDiffCallback : DiffUtil.ItemCallback<film>() {
    override fun areItemsTheSame(oldItem: film, newItem: film): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: film, newItem: film): Boolean {
        return oldItem == newItem
    }

}

class Adaptersiparis() : ListAdapter<film, siparisviewh>(FilmDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): siparisviewh {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.siparistasarim, parent, false)
        return siparisviewh(v)
    }

    override fun onBindViewHolder(holder: siparisviewh, position: Int) {
        val item = getItem(position)

        holder.fiyatsip.text = "${item.fiyat} TL"
        Glide.with(holder.itemView.context)
            .load(item.posterurl)
            .into(holder.postersip)
        holder.isimsip.text = item.isim

    }
}

class Adapterbenzer() : ListAdapter<film, benzerviewh>(FilmDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): benzerviewh {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.design2, parent, false)
        return benzerviewh(v)
    }

    override fun onBindViewHolder(holder: benzerviewh, position: Int) {
        val item = getItem(position)
        holder.fiyat.text = item.fiyat.toString() + "₺"
        holder.isim.text = item.isim
        holder.puan.text = item.puan.toString()
        Glide.with(holder.itemView.context).load(item.posterurl).into(holder.poster)
        holder.imdb.setImageResource(R.drawable.imdb)
        holder.agerange.visibility = if (item.agerange) View.VISIBLE else View.INVISIBLE
        holder.itemView.setOnClickListener {
            (holder.itemView.context as Activity).finish()
            val intent = Intent(holder.itemView.context, detayekran::class.java)
            intent.putExtra("index", item.id)
            holder.itemView.context.startActivity(intent)
        }
    }
}

class adaptersearch : ListAdapter<film, arama_veiewh>(FilmDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): arama_veiewh {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.aramadesign, parent, false)
        return arama_veiewh(view)
    }

    override fun onBindViewHolder(holder: arama_veiewh, position: Int) {
        val item = getItem(position)
        holder.isimarama.text = item.isim
        holder.aramayil.text = item.yapımyil
        holder.fiyatarama.text = "${item.fiyat} TL"
        Glide.with(holder.itemView.context).load(item.posterurl).into(holder.posterarama)
        holder.itemView.setOnClickListener{
            if(!sonarananlarlist.contains(item))
                if(sonarananlarlist.size<=9){
                    sonarananlarlist.add(item)
                }
                else{
                    sonarananlarlist.remove(sonarananlarlist.last())
                    sonarananlarlist.add(item)
                }
            val intent = Intent(holder.itemView.context, detayekran::class.java)
            intent.putExtra("index", item.id)
            holder.itemView.context.startActivity(intent)
            submitList(sonarananlarlist.reversed())
        }
    }
}

class AdapterDevam() : ListAdapter<film, viewhIzlemeyeDevamEdin>(FilmDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewhIzlemeyeDevamEdin {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.design3, parent, false)
        return viewhIzlemeyeDevamEdin(view)
    }
    override fun onBindViewHolder(holder: viewhIzlemeyeDevamEdin, position: Int) {
        val item = getItem(position)
        holder.isim.text = item.isim
        Glide.with(holder.itemView.context).load(item.posterurl).placeholder(R.drawable.play_button).into(holder.posterdevam)
    }

}

class Adapterkutuphane() : ListAdapter<film, viewhKutuphane>(FilmDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewhKutuphane {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.design4, parent, false)
        return viewhKutuphane(view)
    }

    override fun onBindViewHolder(holder: viewhKutuphane, position: Int) {
        val item = getItem(position)
        holder.isim.text = item.isim
        Glide.with(holder.itemView.context).load(item.posterurl).into(holder.poster)
    }

}
