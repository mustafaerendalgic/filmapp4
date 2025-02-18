package com.example.film

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.room.Room

class MyMoviesFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies, container, false)
        val db = filmdatabase.getthetable(requireContext())
        val viewm = ViewModelProvider(this, Viewmodelfactory(db.filmDao())).get(View_model::class.java)
        val adapterDevam = AdapterDevam()
        val adapterKutuphane = Adapterkutuphane()
        val kedi = view.findViewById<ImageView>(R.id.kedi)
        val bosyazi = view.findViewById<TextView>(R.id.bursaibostext)
        val izlemeyedevam = view.findViewById<TextView>(R.id.izlemeyedevam)
        val kutuphaneniz =view.findViewById<TextView>(R.id.kütüphaneniz)
        kedi.visibility = View.INVISIBLE
        bosyazi.visibility = View.INVISIBLE
        var size = 0

        viewm.boughtmovies.observe(viewLifecycleOwner){data ->
            size = data.size
            adapterDevam.submitList(data)
            adapterKutuphane.submitList(data)
            if (data.isEmpty()){
                izlemeyedevam.visibility = View.INVISIBLE
                kutuphaneniz.visibility = View.INVISIBLE
                kedi.visibility = View.VISIBLE
                bosyazi.visibility = View.VISIBLE
            }
           else {
            izlemeyedevam.visibility = View.VISIBLE
            kutuphaneniz.visibility = View.VISIBLE
            kedi.visibility = View.INVISIBLE
            bosyazi.visibility = View.INVISIBLE
            }
        }

        val density = requireContext().resources.displayMetrics.density
        val screenwidth = requireContext().resources.displayMetrics.widthPixels
        val cardvLength = (density * 120)
        val totalSpace = screenwidth - (3 * cardvLength)
        val space = totalSpace / 4f
        val rv = view.findViewById<RecyclerView>(R.id.devamRV)
        val rvkutuphane = view.findViewById<RecyclerView>(R.id.kütüphanerv)
        rvkutuphane.addItemDecoration(itemdecfortriple(space.toInt()))
        rv.addItemDecoration(customDesignCodes(size, 20, true))
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rv)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val layoutManagerGrid = GridLayoutManager(requireContext(), 3)
        rvkutuphane.layoutManager = layoutManagerGrid
        rv.layoutManager = layoutManager
        rv.adapter = adapterDevam
        rvkutuphane.adapter = adapterKutuphane

        return view
    }
}