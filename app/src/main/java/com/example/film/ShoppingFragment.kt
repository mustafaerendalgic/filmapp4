package com.example.film

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ShoppingFragment : Fragment() {

    lateinit var adapter2 : Adaptermain

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val db = filmdatabase.getthetable(requireContext())
        val view = inflater.inflate(R.layout.fragment_shopping, container, false)
        val viewm = ViewModelProvider(this, Viewmodelfactory(db.filmDao())).get(View_model::class.java)
        val rv = view.findViewById<RecyclerView>(R.id.rev)

        val sepetclick = view.findViewById<ImageView>(R.id.sepet)
        val bigposter = view.findViewById<ImageView>(R.id.posterbig)

        viewm.initializefilmlist()

        Glide.with(view.context)
            .load("https://wallpapers.com/images/hd/movie-poster-background-q1zm830g0hfww2lk.jpg")
            .into(bigposter)

        val screenwidth = view.context.resources.displayMetrics.widthPixels
        val density = view.context.resources.displayMetrics.density
        val cardwidth = (density * 165).toInt()
        val space = (screenwidth - 2*cardwidth)/3

        adapter2 = Adaptermain()

        viewm.whatsnewlist.observe(viewLifecycleOwner){data ->
            lifecycleScope.launch(Dispatchers.Main){  adapter2.submitList(data.toList()) } }

        val laym = GridLayoutManager(requireContext(), 2)

        laym.orientation = GridLayoutManager.VERTICAL
        rv.layoutManager = laym
        rv.adapter = adapter2

        rv.addItemDecoration(customDesignCodesfor2(2, space, true))

        sepetclick.setOnClickListener {
            val siparisintent = Intent(requireContext(), siparis::class.java)
            startActivity(siparisintent)
        }

        return view
    }
}