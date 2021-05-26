package com.udacity.asteroidradar.main

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.detail.DetailFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var adapter: AsteroidsAdapter

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        // We let the viewModel handle the click event
        adapter = AsteroidsAdapter(AsteroidListener {
           asteroid ->viewModel.onAsteroidClicked(asteroid)
        })

        viewModel.navigateToAsteroidDetails.observe(viewLifecycleOwner, Observer {asteroid ->
            asteroid?.let {
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(asteroid))
                viewModel.onNavigationCompleted()
            }

        })

        binding.asteroidRecycler.adapter = adapter
         recyclerView = binding.asteroidRecycler

        viewModel.asteroidsList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.show_week_asteroids -> viewModel.onWeekAsteroidsSelected()
            R.id.show_today_asteroid -> viewModel.onTodayAsteroidSelected()
            R.id.show_saved_asteroids -> viewModel.onSavedAsteroidsSelected()
        }
        viewModel.asteroidsList.observe(viewLifecycleOwner){

            adapter.submitList(it)

            // Added a small so that smoothScrollToPosition works as intended
            val handler = Handler()
            val runnable = Runnable {
                recyclerView.smoothScrollToPosition(0)
            }
            handler.postDelayed(runnable,100)

        }
        return true
    }
}
