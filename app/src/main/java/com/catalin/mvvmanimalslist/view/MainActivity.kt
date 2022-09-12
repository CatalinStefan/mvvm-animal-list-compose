package com.catalin.mvvmanimalslist.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.catalin.mvvmanimalslist.R
import com.catalin.mvvmanimalslist.api.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private var adapter = AnimalListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUI()
        setupObservables()
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
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
        mainViewModel.result.observe(this) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    buttonFetchAnimals.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
                is NetworkResult.Error -> {
                    progressBar.visibility = View.GONE
                    buttonFetchAnimals.visibility = View.GONE
                    Toast.makeText(this@MainActivity, result.message, Toast.LENGTH_SHORT).show()
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