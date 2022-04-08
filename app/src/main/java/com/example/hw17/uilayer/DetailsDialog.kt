package com.example.hw17.uilayer

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.view.isGone
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.hw17.databinding.DialogDetailsBinding
import com.example.hw17.localmodel.DetailedMovie

class DetailsDialog(private val detailedMovie: DetailedMovie) : DialogFragment() {
    private var _binding: DialogDetailsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogDetailsBinding.inflate(layoutInflater)
        initSetViews()


        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root).create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun initSetViews() {
        if (detailedMovie.backdrop_path == null) binding.backdrop.isGone = true
        else Glide.with(this).load("https://image.tmdb.org/t/p/w500${detailedMovie.backdrop_path}").into(binding.backdrop)
        binding.title.text = detailedMovie.title
        binding.overview.text = "Overview: " + detailedMovie.overview
        binding.budget.text = "Budget: " + detailedMovie.budget + "$"
        binding.genres.text = "Genres: " + detailedMovie.genres.toString()
        binding.releaseDate.text = "Release date: " + detailedMovie.release_date
        binding.productionCompanies.text = "Produced by: " + detailedMovie.production_companies.toString()
        binding.productionCountries.text = "Produced in: " + detailedMovie.production_countries.toString()
        binding.voteAverage.text = "Voted " + detailedMovie.voteAverage + " of 10."
        binding.runtime.text = "Duration: " + detailedMovie.runtime + " min"
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}