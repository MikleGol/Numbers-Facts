package com.miklegol.numbersfacts.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.miklegol.numbersfacts.viewmodels.FactViewModel
import com.miklegol.numbersfacts.viewmodels.FactViewModelFactory
import com.miklegol.numbersfacts.adapters.HistoryAdapter
import com.miklegol.numbersfacts.databinding.ActivityMainBinding
import com.miklegol.numbersfacts.models.Fact
import com.miklegol.numbersfacts.database.FactDatabase
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var binding: ActivityMainBinding
    val viewModel: FactViewModel by lazy {
        val factDatabase = FactDatabase.getInstance(this)
        val factViewModelProviderFactory = FactViewModelFactory(factDatabase)
        ViewModelProvider(this, factViewModelProviderFactory)[FactViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideUI()
        historyAdapter = HistoryAdapter(binding.rvHistory)
        binding.rvHistory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,true)
        binding.rvHistory.adapter = historyAdapter
        viewModel.observerFactDetailsLiveData().observe(this, Observer{ facts ->
            historyAdapter.setHistory(facts)
        })
        onFactClick()

        binding.btnGetFact.setOnClickListener {
            var number = binding.etNumberInput.text.toString()
            if(number.isNotEmpty()){
                viewModel.getFact(number,
                    onSuccess = { fact ->
                        binding.tvFactNumber.text = "${fact.number}"
                        binding.tvFactText.text = "${fact.text}"
                        historyAdapter.addFact(fact)
                    },
                    onFailure = { throwable ->
                        Log.d("Error",throwable.message.toString())
                    })
            } else{
                Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show()
            }
            hideKeyboard()
        }

        binding.btnGetRandomFact.setOnClickListener {
            viewModel.getRandomFact( onSuccess = { fact ->
                binding.tvFactNumber.text = "${fact.number}"
                binding.tvFactText.text = "${fact.text}"
                historyAdapter.addFact(fact)
            },
                onFailure = { throwable ->
                    Log.d("Error",throwable.message.toString())
                })
            hideKeyboard()
        }
    }

    private fun onFactClick() {
        historyAdapter.onItemClicked(object : HistoryAdapter.OnItemHistoryClicked{
            override fun onClickListener(fact: Fact) {
                val intent = Intent(applicationContext, FactDetailActivity::class.java)
                intent.putExtra("FactId",fact.idFact)
                startActivity(intent)
            }
        })
    }

    private fun hideUI(){
        supportActionBar?.hide()
        val window = window
        val decorView = window.decorView
        decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                )
    }

    private fun hideKeyboard() {
        currentFocus?.let { view ->
            (this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(this.currentFocus!!.windowToken, 0)
        }
    }
}