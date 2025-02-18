package com.example.film

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.film.BrowserFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class arama : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var searchJob : Job? = null
        val view = inflater.inflate(R.layout.fragment_arama, container, false)
        val db = filmdatabase.getthetable(requireContext())
        val viewm = ViewModelProvider(this, Viewmodelfactory(db.filmDao())).get(View_model::class.java)
        val aramametni = view.findViewById<EditText>(R.id.aramametni)
        aramametni.requestFocus()
        aramametni.post {
            val immb = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            immb.showSoftInput(aramametni, InputMethodManager.SHOW_IMPLICIT)
        }

        fun searchDatabase(aramametni:String, adapter : adaptersearch){
            lifecycleScope.launch {
                val filtered = withContext(Dispatchers.IO){ db.filmDao().getthefilteredlist(aramametni)}
                adapter.submitList(filtered)
            }
        }

        val adapter = adaptersearch()

        viewm.whatsnewlist.observe(viewLifecycleOwner){data ->
            adapter.submitList(data)}

        val rvarama = view.findViewById<RecyclerView>(R.id.aramarv)
        rvarama.adapter = adapter
        val laym = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvarama.layoutManager = laym
        rvarama.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))

        aramametni.addTextChangedListener (object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                searchJob?.cancel()
                searchJob = lifecycleScope.launch {
                    delay(100)
                    searchDatabase(p0.toString(), adapter)
                }
            }
        }
        )

        val backb = view.findViewById<ImageView>(R.id.backbutton)


        fun geridon(){
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.framelayout1, BrowserFragment2()).commit()
        }

        backb.setOnClickListener {
           geridon()

        }

        requireActivity().onBackPressedDispatcher.addCallback {
            geridon()
        }

        return view
    }

}