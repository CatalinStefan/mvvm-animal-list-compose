package com.catalin.mvvmanimalslist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.catalin.mvvmanimalslist.R
import com.catalin.mvvmanimalslist.api.AnimalService
import com.catalin.mvvmanimalslist.model.Animal
import kotlinx.android.synthetic.main.fragment_animal_detail.*


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
        return inflater.inflate(R.layout.fragment_animal_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animal?.let {
            val url = AnimalService.BASE_URL + it.image
            Glide.with(detail_image.context)
                .load(url)
                .into(detail_image)
            detail_title.text = it.name
            detail_location.text = getString(R.string.detail_location, it.location)
            detail_lifespan.text = getString(R.string.detail_lifespan, it.lifespan)
            detail_diet.text = getString(R.string.detail_diet, it.diet)
        }
    }

    companion object {
        const val currentAnimal = "currentAnimal"
    }
}