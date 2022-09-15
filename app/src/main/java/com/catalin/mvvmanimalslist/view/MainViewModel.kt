package com.catalin.mvvmanimalslist.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.catalin.mvvmanimalslist.api.AnimalRepo
import com.catalin.mvvmanimalslist.api.NetworkResult
import com.catalin.mvvmanimalslist.model.Animal
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: AnimalRepo) : ViewModel() {

    val result = MutableLiveData<NetworkResult<List<Animal>>>()

    init {
        result.value = NetworkResult.Initial()
    }

    fun getAnimals() {
        result.value = NetworkResult.Loading()
        repo.getAnimals()
            .enqueue(object : Callback<List<Animal>> {
                override fun onResponse(call: Call<List<Animal>>, response: Response<List<Animal>>) {
                    if (response.isSuccessful)
                        response.body()?.let { result.value = NetworkResult.Success(it) }
                    else
                        result.value = NetworkResult.Error(response.message())
                }

                override fun onFailure(call: Call<List<Animal>>, t: Throwable) {
                    t.localizedMessage?.let { result.value = NetworkResult.Error(it) }
                    t.printStackTrace()
                }

            })
    }

}