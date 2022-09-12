package com.catalin.mvvmanimalslist.api

class AnimalRepo(private val api: AnimalApi) {
    fun getAnimals() = api.getAnimals()
}