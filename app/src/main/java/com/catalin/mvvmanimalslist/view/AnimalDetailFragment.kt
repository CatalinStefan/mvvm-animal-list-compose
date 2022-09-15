package com.catalin.mvvmanimalslist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.catalin.mvvmanimalslist.R
import com.catalin.mvvmanimalslist.api.AnimalService
import com.catalin.mvvmanimalslist.databinding.FragmentAnimalDetailBinding
import com.catalin.mvvmanimalslist.model.Animal


class AnimalDetailFragment : Fragment() {
    private var animal: Animal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            animal = it.getParcelable(currentAnimal)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAnimalDetailBinding.inflate(inflater, container, false)
            .apply {
                animal?.let {
                    val url = AnimalService.BASE_URL + it.image
                    Glide.with(detailImage.context)
                        .load(url)
                        .into(detailImage)
                    detailTitle.text = it.name
                    detailLocation.text = getString(R.string.detail_location, it.location)
                    detailLifespan.text = getString(R.string.detail_lifespan, it.lifespan)
                    detailDiet.text = getString(R.string.detail_diet, it.diet)
                }
            }

        return binding.root
    }

    companion object {
        const val currentAnimal = "currentAnimal"
    }
}






