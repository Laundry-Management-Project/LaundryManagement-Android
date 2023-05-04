package project.laundry.presentation.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import project.laundry.databinding.ActivityOwReservationDetailBinding

class OwReservationDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityOwReservationDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()

        setContentView(binding.root)
    }
    fun initView(){
        binding = ActivityOwReservationDetailBinding.inflate(layoutInflater)
    }
}