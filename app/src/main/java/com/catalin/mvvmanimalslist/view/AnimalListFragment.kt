package com.catalin.mvvmanimalslist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.catalin.mvvmanimalslist.R
import com.catalin.mvvmanimalslist.api.NetworkResult
import com.catalin.mvvmanimalslist.model.Animal
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_animal_list.*

@AndroidEntryPoint
class AnimalListFragment : Fragment() {

    private val mainViewModel by viewModels<MainViewModel>()
    private val clickCallback: (animal: Animal) -> Unit = {
        val bundle = bundleOf(AnimalDetailFragment.currentAnimal to it)
        view?.findNavController()?.navigate(R.id.action_animalListFragment_to_animalDetailFragment, bundle)
    }
    private var adapter = AnimalListAdapter(arrayListOf(), clickCallback)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_animal_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObservables()
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.run {
            addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    (recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
        }
        recyclerView.adapter = adapter
        buttonFetchAnimals.setOnClickListener {
            mainViewModel.getAnimals()
        }
    }

    private fun setupObservables() {
        mainViewModel.result.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    buttonFetchAnimals.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
                is NetworkResult.Error -> {
                    progressBar.visibility = View.GONE
                    buttonFetchAnimals.visibility = View.GONE
                    Toast.makeText(this.context, result.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Success -> {
                    progressBar.visibility = View.GONE
                    buttonFetchAnimals.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    result.data?.let {
                        adapter.newAnimals(it)
                    }
                }
            }
        }
    }
}