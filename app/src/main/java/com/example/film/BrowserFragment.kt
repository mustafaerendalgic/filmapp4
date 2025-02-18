package com.example.film

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout


class BrowserFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_browser, container,false)
        val fragment = listOf(arama(), BrowserFragment2())
        val frame = view.findViewById<ConstraintLayout>(R.id.browserframe)



        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.framelayout1, BrowserFragment2()).commit()


        return view
    }

}