package com.catalin.mvvmanimalslist.api

import com.catalin.mvvmanimalslist.model.Animal
import retrofit2.Call
import retrofit2.http.GET

interface AnimalApi {

    @GET("animals.json")
    fun getAnimals(): Call<List<Animal>>

}