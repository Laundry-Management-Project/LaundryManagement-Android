package project.laundry.presentation.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import project.laundry.databinding.ActivityOwReservationDetailBinding
import project.laundry.presentation.viewmodel.OwReservationDetailViewModel

class OwReservationDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityOwReservationDetailBinding
    lateinit var reId:String
    val viewModel = OwReservationDetailViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()

        val intent = intent
        intent.getStringExtra("re_id")?.let{
            reId = it
        }

        viewModel.getReservation(reId)
        viewModel.reservationData.observe(this, Observer {

        })

        setContentView(binding.root)
    }
    fun initView(){
        binding = ActivityOwReservationDetailBinding.inflate(layoutInflater)
    }
}