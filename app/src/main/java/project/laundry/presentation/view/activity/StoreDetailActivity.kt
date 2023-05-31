package project.laundry.presentation.view.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import project.laundry.R
import project.laundry.data.App
import project.laundry.data.dataclass.AddReservation
import project.laundry.databinding.ActivityStoreDetailBinding
import project.laundry.presentation.view.StoreImagePagerAdapter
import project.laundry.presentation.viewmodel.OwHomeViewModel
import project.laundry.presentation.viewmodel.StoreDetailViewModel
import java.io.Serializable

class StoreDetailActivity : AppCompatActivity() {

    lateinit var binding : ActivityStoreDetailBinding

    lateinit var viewModel :StoreDetailViewModel

    val uid=App.prefs.uid!!
    val userType = App.prefs.userType!!
    val buId=App.prefs.buId!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val viewModelProvider = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
        viewModel = viewModelProvider[StoreDetailViewModel::class.java]

        initView()

        viewModel.loadDetail(userType, buId)

        viewModel.bitmaps.observe(this){bitmaps->
            if(bitmaps.isNotEmpty()){
                Log.d("bitmapsSize", bitmaps.size.toString())
                binding.viewPager.adapter = StoreImagePagerAdapter(this, bitmaps)
            }

        }

        viewModel.loading.observe(this){isLoading ->
            if(isLoading){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }
        }
        viewModel.store.observe(this){store ->
            binding.tvStoreName.text = store.name
            binding.tvBuHours.text = store.bu_hour
            binding.tvIntro.text = store.intro
            binding.tvAddress.text = store.address
            binding.tvContact.text = store.contact
        }
//
//        val fm = supportFragmentManager
//        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
//            ?: MapFragment.newInstance().also {
//                fm.beginTransaction().add(R.id.map, it).commit()
//            }
//        mapFragment?.getMapAsync(this)

        setContentView(binding.root)

        fun <T: Serializable> Intent.intentSerializable(key: String, clazz: Class<T>): T? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                this.getSerializableExtra(key, clazz)
            } else {
                this.getSerializableExtra(key) as T?
            }
        }

    }
    private fun initView(){
        binding = ActivityStoreDetailBinding.inflate(layoutInflater)
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.btnRegister.setOnClickListener {
            Log.d("buttonclick", "success")
            val intent = Intent(this, AddReservationActivity::class.java)
            intent.putExtra("bu_id", buId)
            startActivity(intent)
        }

    }
//
//    override fun onMapReady(p0: NaverMap) {
//        TODO("Not yet implemented")
//    }
}