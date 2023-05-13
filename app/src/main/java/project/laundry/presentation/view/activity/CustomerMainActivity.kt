package project.laundry.presentation.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import project.laundry.R
import project.laundry.databinding.ActivityCustomerMainBinding
import project.laundry.presentation.view.fragments.CuStoresFragmnet
import project.laundry.presentation.view.fragments.CuReservationsFragment

class CustomerMainActivity : AppCompatActivity() {
    lateinit var binding : ActivityCustomerMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()

        setContentView(binding.root)
    }
    private fun initView(){
        binding = ActivityCustomerMainBinding.inflate(layoutInflater)

        val cuStoresFragmnet = CuStoresFragmnet()
        val cuReservationsFragment = CuReservationsFragment()
        val fragList = mutableListOf(cuStoresFragmnet, cuReservationsFragment)
        var currentFragment: Fragment?=fragList[0]

        supportFragmentManager.beginTransaction().add(R.id.frameLayout, fragList[0]).commit()
        supportFragmentManager.beginTransaction().show(fragList[0]).commit()

        binding.bottomNavigation.setOnItemSelectedListener() { item ->
            Log.d("fragmnetid", item.itemId.toString())
            val index = when (item.itemId) {
                R.id.store -> 0
                R.id.reservation -> 1
                else -> -1
            }
            Log.d("fragmentid", index.toString())
            if(index>=0){
                val fragment = fragList[index]
                if (currentFragment != fragment) {
                    currentFragment?.let { supportFragmentManager.beginTransaction().hide(it).commit() }
                    currentFragment = fragment
                    if (!fragment.isAdded) {
                        supportFragmentManager.beginTransaction().add(R.id.frameLayout, fragment).commit()
                    }
                    supportFragmentManager.beginTransaction().show(fragment).commit()
//                    currentFragment?.arguments = bundle
                }
            }
            true
        }
    }
}