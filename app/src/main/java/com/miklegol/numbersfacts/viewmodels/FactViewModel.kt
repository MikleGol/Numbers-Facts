package com.miklegol.numbersfacts.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miklegol.numbersfacts.models.Fact
import com.miklegol.numbersfacts.database.FactDatabase
import com.miklegol.numbersfacts.network.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import java.util.*
import retrofit2.Response

class FactViewModel(private val factDatabase: FactDatabase) : ViewModel() {

    private var allFactsLiveData = MutableLiveData<List<Fact>>()
    init {
        viewModelScope.launch {
            allFactsLiveData.value = factDatabase.factDao().getAllFacts()
        }
    }

    fun observerFactDetailsLiveData(): LiveData<List<Fact>>{
        return allFactsLiveData
    }

    fun insertFact(fact: Fact){
        viewModelScope.launch {
            factDatabase.factDao().insertFact(fact)
        }
    }

    fun deleteFact(fact: Fact){
        viewModelScope.launch {
            factDatabase.factDao().deleteFact(fact)
        }
    }

    fun getFact(number: String, onSuccess: (Fact) -> Unit, onFailure: (Throwable) -> Unit) {
        viewModelScope.launch {
            try {
                RetrofitInstance.api.getFact(number).enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if(response.body() != null){
                            val (numberOfFact, textOfFact) = response.body()!!.split(" ", limit = 2)
                            val fact = Fact(UUID.randomUUID().toString(), numberOfFact, textOfFact)
                            insertFact(fact)
                            onSuccess(fact)
                        }
                    }
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.d("Error",t.message.toString())
                        onFailure(t)
                    }
                })
            } catch (e: Exception) {
                Log.e("FactViewModel", "Error getting fact: ${e.message}", e)
                onFailure(e)
            }
        }
    }


    fun getRandomFact(onSuccess: (Fact) -> Unit, onFailure: (Throwable) -> Unit) {
        viewModelScope.launch {
            try {
                RetrofitInstance.api.getRandomFact().enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if(response.body() != null){
                            val (numberOfFact, textOfFact) = response.body()!!.split(" ", limit = 2)
                            val fact = Fact(UUID.randomUUID().toString(), numberOfFact, textOfFact)
                            insertFact(fact)
                            onSuccess(fact)
                        }
                    }
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.d("Error",t.message.toString())
                        onFailure(t)
                    }
                })
            } catch (e: Exception) {
                Log.e("FactViewModel", "Error getting fact: ${e.message}", e)
                onFailure(e)
            }
        }
    }
}
