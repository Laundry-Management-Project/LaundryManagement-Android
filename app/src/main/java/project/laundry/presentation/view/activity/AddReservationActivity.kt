package project.laundry.presentation.view.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import project.laundry.R
import project.laundry.data.App
import project.laundry.data.dataclass.AddReservation
import project.laundry.databinding.ActivityReservationAddBinding
import project.laundry.presentation.view.ImageAdapter

import project.laundry.presentation.viewmodel.AddReservationViewModel

class AddReservationActivity : AppCompatActivity() {
    lateinit var binding : ActivityReservationAddBinding

    val uid = App.prefs.uid!!
    val buId = App.prefs.buId!!
    val userType = App.prefs.userType!!
    private lateinit var arrUri : ArrayList<Uri>

    lateinit var viewModel : AddReservationViewModel


    private val childForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        when (result.resultCode){
            RESULT_OK -> {
                val uri : Uri? = result.data?.data
                Log.d("uriImgae", uri.toString())

                if(uri == null){
                    Toast.makeText(this, "잘못된 이미지 입니다.", Toast.LENGTH_SHORT).show()
                }
                else{
                    viewModel.addImage(uri)
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arrUri = ArrayList()

        initView()

        setContentView(binding.root)
    }
    private fun initView(){
        binding = ActivityReservationAddBinding.inflate(layoutInflater)

        binding.imageRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }

        val viewModelProvider = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
        viewModel= viewModelProvider[AddReservationViewModel::class.java]

        viewModel.imageUris.observe(this){
            binding.imageRecycler.adapter = ImageAdapter(this, it)
            Log.d("imgaeobserver", "ok")
        }

        viewModel.myReservation.observe(this){ reservation ->
            if(reservation.re_id.isNotEmpty()){
                finish()
            }
        }
        viewModel.loading.observe(this){isLoading->
            if(isLoading){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }

        }

        var laundryNum : Int = 0
        val items = resources.getStringArray(R.array.num_array)
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        binding.spinnerLaundry.adapter = spinnerAdapter
        binding.spinnerLaundry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if(position != 0){
                    laundryNum = items[position].toInt()
                }


            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        binding.addImage.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            childForResult.launch(galleryIntent)
        }
        binding.btnRegister.setOnClickListener {

            val rd = AddReservation(binding.etDetails.text.toString(), binding.etClothingType.text.toString(),laundryNum)
            viewModel.addReservation(uid, buId, rd)

        }
    }

}