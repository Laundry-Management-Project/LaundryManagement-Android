package project.laundry.presentation.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.UiThread
import androidx.lifecycle.Observer
import com.naver.maps.map.MapFragment


import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import project.laundry.R
import project.laundry.data.dataclass.AddStore
import project.laundry.databinding.ActivityStoreRegisterBinding
import project.laundry.presentation.viewmodel.StoreRegisterViewModel

class StoreRegisterActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var binding : ActivityStoreRegisterBinding

    private val viewModel = StoreRegisterViewModel()
    lateinit var uid : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreference = getSharedPreferences("User", MODE_PRIVATE)
        sharedPreference.getString("uid", "")?.let{
            uid = it
            if(uid == ""){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        binding = ActivityStoreRegisterBinding.inflate(layoutInflater)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }
        mapFragment?.getMapAsync(this)

        binding.btnRegister.setOnClickListener {
            val addStoreDto = AddStore(
                binding.etStoreAddress.text.toString(),
                binding.etBuHours.text.toString(),
                binding.etStoreName.text.toString(),
                binding.etContact.text.toString(),
                binding.etIntro.text.toString()
            )
            viewModel.addStore(uid, addStoreDto)
        }
        viewModel.storeList.observe(this, Observer { storeList ->
            if(storeList!=null){
                val returnIntent = Intent(this, StoreListActivity::class.java)
//                returnIntent.putExtra("storeItem", storeList)
                returnIntent.putExtra("test", "test")
                setResult(RESULT_OK, returnIntent)
                finish()
            }
        })
        setContentView(binding.root)

        fun init_webViewDialog(){
            val builder = MaterialAlertDialogBuilder(this)

            val viewGroup = findViewById<ViewGroup>(android.R.id.content)
            val view = LayoutInflater.from(this).inflate(R.layout.dialog_webview, viewGroup, false)

            val webView = view.findViewById<WebView>(R.id.web_view)

            webView.apply {
                settings.apply {
                    javaScriptEnabled = true
                    javaScriptCanOpenWindowsAutomatically = true
                    domStorageEnabled = true
                }
//                webChromeClient
//                webViewClient
//                addJavascriptInterface()
                loadUrl("https://laundry-management-ywsj-team.koyeb.app/roadSearch.html")

            }

            builder.apply {
                setView(view)
            }

            builder.show()

        }
    }
    @UiThread
    override fun onMapReady(_naverMap: NaverMap) {

    }

}