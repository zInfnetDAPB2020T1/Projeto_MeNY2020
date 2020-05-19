package com.example.projeto_meny2020.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projeto_meny2020.R
import com.example.projeto_meny2020.adapter.RecyclerViewDailyAdapter
import com.example.projeto_meny2020.classes.modelsRetrofit.Forecast
import com.example.projeto_meny2020.viewModel.DadosTempoViewModel
import kotlinx.android.synthetic.main.fragment_gallery.*

class GalleryFragment : Fragment() {

//    private lateinit var galleryViewModel: GalleryViewModel
    private lateinit var dadosTempoViewModel: DadosTempoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity!!.let {
            dadosTempoViewModel = ViewModelProviders.of(it)[DadosTempoViewModel::class.java]
        }
//        galleryViewModel =
//            ViewModelProviders.of(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)
//        val textView: TextView = root.findViewById(R.id.text_gallery)
//        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var popular: MutableList<Forecast> = mutableListOf()
        dadosTempoViewModel.dadosDaily!!.data!!.forEach {
            popular.add(it)
        }

        val dailyAdapter = RecyclerViewDailyAdapter(popular)

        dailyRecyclerVwDias.adapter = dailyAdapter
        dailyRecyclerVwDias.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}
