package com.example.hw17.uilayer

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw17.MyAdapter
import com.example.hw17.MyApplication
import com.example.hw17.databinding.FragmentMovieListBinding


class MovieListFragment : Fragment() {
    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel:
            SharedViewModel by activityViewModels { SharedViewModelFactory((requireActivity().application as MyApplication).appContainer.repository) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSetObservers()

        if (isInternetAvailable()) sharedViewModel.getMoviesFromNet()
        else sharedViewModel.getMoviesFromDatabase()

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (isInternetAvailable()) sharedViewModel.searchMovies(query ?: "")
                else Toast.makeText(requireContext(), "You need internet connection.", Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun initSetObservers() {
        sharedViewModel.movieLiveData.observe(viewLifecycleOwner) {
            val myAdapter = MyAdapter(it)
            myAdapter.onViewClicked(object : MyAdapter.ViewClick {
                override fun onClick(pos: Int) {
                    if (isInternetAvailable()) sharedViewModel.getMovieDetails(it[pos].id)
                    else Toast.makeText(requireContext(), "You need internet connection.", Toast.LENGTH_SHORT).show()

                }
            })
            myAdapter.submitList(it)
            binding.recyclerView.apply {
                adapter = myAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
        sharedViewModel.movieDetailsLiveData.observe(viewLifecycleOwner) {
            DetailsDialog(it).show(childFragmentManager, "details")
        }
    }

    private fun isInternetAvailable(): Boolean {
        var result: Boolean
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
        return result
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}