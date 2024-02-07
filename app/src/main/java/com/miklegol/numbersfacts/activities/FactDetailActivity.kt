package com.miklegol.numbersfacts.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.miklegol.numbersfacts.R
import com.miklegol.numbersfacts.database.FactDatabase
import com.miklegol.numbersfacts.databinding.ActivityFactDetailBinding
import com.miklegol.numbersfacts.viewmodels.FactViewModel
import com.miklegol.numbersfacts.viewmodels.FactViewModelFactory

class FactDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFactDetailBinding

    val viewModel: FactViewModel by lazy {
        val factDatabase = FactDatabase.getInstance(this)
        val factViewModelProviderFactory = FactViewModelFactory(factDatabase)
        ViewModelProvider(this, factViewModelProviderFactory)[FactViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fact_detail)
        binding = ActivityFactDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideUI()

        val factId = intent.getSerializableExtra("FactId")

        viewModel.observerFactDetailsLiveData().observe(this, Observer{ facts ->
            for(i in facts){
                if(factId!!.equals(i.idFact)){
                    binding.tvFactNumber.text = i.number
                    binding.tvFactText.text = i.text
                }
            }
        })

        binding.btnBack.setOnClickListener {
            this.onBackPressed()
        }
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
}
