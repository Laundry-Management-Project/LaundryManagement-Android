package project.laundry.presentation.view.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import androidx.annotation.UiThread
import androidx.lifecycle.Observer
import com.naver.maps.map.MapFragment


import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder

import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import project.laundry.R
import project.laundry.data.App
import project.laundry.data.dataclass.AddStore
import project.laundry.databinding.ActivityStoreRegisterBinding
import project.laundry.presentation.view.ImageAdapter
import project.laundry.presentation.viewmodel.AddReservationViewModel
import project.laundry.presentation.viewmodel.StoreRegisterViewModel

class OwStoreRegisterActivity : AppCompatActivity() {
    lateinit var binding : ActivityStoreRegisterBinding

    val uid = App.prefs.uid!!

    lateinit var viewModel :StoreRegisterViewModel

    private val arrUri = ArrayList<Uri>()
    private val childForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        when (result.resultCode){
            RESULT_OK -> {
                val uri : Uri? = result.data?.data

                if(uri == null){
                    Toast.makeText(this, "잘못된 이미지 입니다.", Toast.LENGTH_SHORT).show()
                }
                else{
                    viewModel.addImage(uri)
                }
            }
        }
    }
    private val childForResult2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        when (result.resultCode){
            RESULT_OK -> {
                binding.etStoreAddress.text = Editable.Factory.getInstance().newEditable(result.data?.getStringExtra("address"))
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()


        val viewModelProvider = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
        viewModel = viewModelProvider[StoreRegisterViewModel::class.java]

        viewModel.imageUris.observe(this){
            binding.imageRecycler.adapter = ImageAdapter(this, it)
            Log.d("imgaeobserver", "ok")
        }
        viewModel.store.observe(this, Observer { store ->
            if(store.bu_id.isNotEmpty()){
                val returnIntent = Intent(this, OwStoresActivity::class.java)
                returnIntent.putExtra("store", store)
                setResult(RESULT_OK, returnIntent)
                finish()
            }
        })

        setContentView(binding.root)

//        fun init_webViewDialog(){
//            val builder = MaterialAlertDialogBuilder(this)
//
//            val viewGroup = findViewById<ViewGroup>(android.R.id.content)
//            val view = LayoutInflater.from(this).inflate(R.layout.dialog_webview, viewGroup, false)
//
//            val webView = view.findViewById<WebView>(R.id.web_view)
//
//            webView.apply {
//                settings.apply {
//                    javaScriptEnabled = true
//                    javaScriptCanOpenWindowsAutomatically = true
//                    domStorageEnabled = true
//                }
////                webChromeClient
////                webViewClient
////                addJavascriptInterface()
//                loadUrl("https://laundry-management-ywsj-team.koyeb.app/roadSearch.html")
//
//            }
//
//            builder.apply {
//                setView(view)
//            }
//
//            builder.show()
//
//        }
    }

    private fun initView(){
        binding = ActivityStoreRegisterBinding.inflate(layoutInflater)

        binding.imageRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.btnRegister.setOnClickListener {
            val addStoreDto = AddStore(
                binding.etStoreAddress.text.toString(),
                binding.etBuHours.text.toString(),
                binding.etStoreName.text.toString(),
                binding.etContact.text.toString(),
                binding.etIntro.text.toString()
            )
            viewModel.addStore(uid, addStoreDto)
            Log.d("arrUriSIze", arrUri.size.toString())
        }

        binding.btnAddImage.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            childForResult.launch(galleryIntent)
        }

        binding.startMap.setOnClickListener {
            val intent = Intent(this, NaverMapActivity::class.java)
            childForResult2.launch(intent)
        }
    }
}