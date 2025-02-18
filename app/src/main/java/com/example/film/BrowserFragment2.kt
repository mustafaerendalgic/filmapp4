package com.example.film

import android.content.Intent
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class BrowserFragment2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val db = filmdatabase.getthetable(requireContext())
        val view = inflater.inflate(R.layout.fragment_browser2, container,false)
        val viewm = ViewModelProvider(this, Viewmodelfactory(db.filmDao())).get(View_model::class.java)
        val donguresim = view.findViewById<ImageView>(R.id.loopimage)
        val donguresimarka = view.findViewById<ImageView>(R.id.loopimagebehind)
        val frame = view.findViewById<FrameLayout>(R.id.framelayout1)
        val search_bar = view.findViewById<ConstraintLayout>(R.id.cl)
        val korkurv = view.findViewById<RecyclerView>(R.id.korku)
        val animerv = view.findViewById<RecyclerView>(R.id.animefilm)
        val fantastikrv = view.findViewById<RecyclerView>(R.id.fantastik)
        val sucrv = view.findViewById<RecyclerView>(R.id.sucrv)
        val serikatilrv = view.findViewById<RecyclerView>(R.id.serikrv)
        val bilimkurgurv = view.findViewById<RecyclerView>(R.id.bilimkurgurv)
        val posterismi = view.findViewById<TextView>(R.id.posterinismi)
        val gradient = view.findViewById<ImageView>(R.id.imageView6)

        val screenwidth = view.context.resources.displayMetrics.widthPixels
        val density = view.context.resources.displayMetrics.density
        val cardwidth = (density * 180).toInt()
        val space = (screenwidth - 2*cardwidth)/3

        viewm.korkulist.observe(viewLifecycleOwner){it-> rvs(korkurv, it, space)}
        viewm.animelist.observe(viewLifecycleOwner){it-> rvs(animerv, it, space)}
        viewm.fantastiklist.observe(viewLifecycleOwner){it-> rvs(fantastikrv, it, space)}
        viewm.suçlist.observe(viewLifecycleOwner){it-> rvs(sucrv, ArrayList(it), space)}
        viewm.serikatillist.observe(viewLifecycleOwner){it-> rvs(serikatilrv, it, space)}
        viewm.bilimkurgulist.observe(viewLifecycleOwner){it-> rvs(bilimkurgurv, it, space)}

        viewLifecycleOwner.lifecycleScope.launch {
            var index = 0
            val maxid = viewm.films.size
            while (isActive && isAdded){
                if (maxid > 0) {
                    var itemTop = viewm.films[index % maxid]
                    index = (index + 1) % maxid
                    var itemNext = viewm.films[index % maxid]
                    Glide.with(requireContext()).load(itemTop.posterurl).into(donguresim)
                    delay(5000)

                    Glide.with(requireContext()).load(itemNext.posterurl).into(donguresimarka)
                    donguresim.animate()
                        .translationX(-donguresim.width.toFloat())
                        .setDuration(500)
                        .withEndAction {
                            posterismi.text = itemNext.isim
                            donguresimarka.bringToFront()
                            donguresim.translationX = 0f
                        }

                    delay(5000)
                    index = (index + 1) % maxid
                    itemTop = viewm.films[index % maxid]
                    Glide.with(requireContext()).load(itemTop.posterurl).into(donguresim)
                    donguresimarka.animate()
                        .translationX(-donguresimarka.width.toFloat())
                        .setDuration(500)
                        .withEndAction {

                            posterismi.text = itemTop.isim
                            donguresim.bringToFront()
                            donguresimarka.translationX = 0f
                        }

                }
                else {
                    Glide.with(view.context)
                        .load(R.drawable.play_button)
                        .into(donguresim)
                }
            }
        }

        /*gradient.setOnClickListener {
            val intent = Intent(requireContext(), detayekran::class.java)
            intent.putExtra("index", currentindex)
            requireContext().startActivity(intent)
        }*/

        search_bar.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.framelayout1, arama()).commit()
        }

        return view
    }

}