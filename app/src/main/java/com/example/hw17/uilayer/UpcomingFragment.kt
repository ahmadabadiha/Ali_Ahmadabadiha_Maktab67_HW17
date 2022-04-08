package com.example.hw17.uilayer

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw17.MyAdapter
import com.example.hw17.MyApplication
import com.example.hw17.databinding.FragmentMovieListBinding
import com.example.hw17.databinding.FragmentUpcomingBinding
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.collect

class UpcomingFragment : Fragment() {
    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel:
            SharedViewModel by activityViewModels { SharedViewModelFactory((requireActivity().application as MyApplication).appContainer.repository) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isInternetAvailable()) Toast.makeText(requireContext(), "You need internet connection.", Toast.LENGTH_SHORT).show()
        else {
            sharedViewModel.getUpcoming()
            initSetObservers()
        }

    }

    private fun initSetObservers() {
        sharedViewModel.upcomingMovieLiveData.observe(viewLifecycleOwner) {
            val myAdapter = MyAdapter(it)
            myAdapter.onViewClicked(object : MyAdapter.ViewClick {
                override fun onClick(pos: Int) {
                    sharedViewModel.getMovieDetails(it[pos].id)
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
        val result: Boolean
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

