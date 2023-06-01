package project.laundry.presentation.view.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.annotation.UiThread
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.coroutines.*
import project.laundry.R
import project.laundry.databinding.ActivityNaverMapBinding
import java.util.*

class NaverMapActivity : AppCompatActivity(), OnMapReadyCallback {
    private val LOCATION_PERMISSION_REQUEST_CODE = 5000

    private val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    lateinit var binding : ActivityNaverMapBinding

    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource

    private val marker = Marker()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaverMapBinding.inflate(layoutInflater)

        if (!hasPermission()) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            initMapView()
        }

        binding.btnRegisterAddress.setOnClickListener {
            val returnIntent = Intent(this, OwStoreRegisterActivity::class.java)
            returnIntent.putExtra("address", binding.address.text.toString())
            setResult(RESULT_OK, returnIntent)
            finish()
        }

        setContentView(binding.root)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 권한이 허용되었을 경우 처리할 로직을 작성합니다.
                    initMapView()
                } else {
                    // 권한이 거부되었을 경우 처리할 로직을 작성합니다.
                    // 예를 들어, 권한이 거부되었을 때 알림을 표시하거나 대체 동작을 수행할 수 있습니다.
                }
            }
        }
    }

    private fun initMapView(){

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_view) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_view, it).commit()
            }
        mapFragment?.getMapAsync(this)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

    }
    private suspend fun getAddressAsync(
        geocoder: Geocoder,
        latitude: Double,
        longitude: Double
    ): String = coroutineScope {
        val deferredResult = async(Dispatchers.IO) {
            var addressString = ""
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                if (addresses != null) {
                    if (addresses.isNotEmpty()) {
                        addressString = addresses[0].getAddressLine(0)
                        Log.d("addressString", addressString)
                    }
                }
            } else {
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                if (addresses != null && addresses.isNotEmpty()) {
                    addressString = addresses[0].getAddressLine(0)
                }
            }
            Log.d("addressString2", addressString)
            addressString = addressString.substring(5, addressString.length)

            addressString
        }

        deferredResult.await()
    }

    private fun hasPermission(): Boolean {
        for (permission in PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    @UiThread
    override fun onMapReady(_naverMap: NaverMap) {
        naverMap=_naverMap

        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        val handler = Handler()
        val delayMillis = 100 // 1초 딜레이

        naverMap.addOnCameraChangeListener { reason, animated ->
            // 카메라 이동이 발생할 때마다 호출되는 콜백
            val center = naverMap.cameraPosition.target

            // 마커 위치를 현재 중심 좌표로 이동
            marker.position = center
            handler.removeCallbacksAndMessages(null) // 기존에 예약된 딜레이 콜백 제거
            handler.postDelayed({
                val geocoder = Geocoder(applicationContext, Locale.KOREAN)

                lifecycleScope.launch {
                    binding.address.text = getAddressAsync(geocoder, marker.position.latitude, marker.position.longitude)
                }


            }, delayMillis.toLong())

        }

        marker.position = LatLng(37.5665, 126.9780) // 마커 위치 설정
        marker.map = naverMap

    }
}