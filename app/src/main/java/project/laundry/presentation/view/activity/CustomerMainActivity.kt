package project.laundry.presentation.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import project.laundry.R
import project.laundry.databinding.ActivityCustomerMainBinding
import project.laundry.presentation.view.fragments.StoresFragmnet
import project.laundry.presentation.view.fragments.ReservationsFragment

class CustomerMainActivity : AppCompatActivity() {
    lateinit var binding : ActivityCustomerMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerMainBinding.inflate(layoutInflater)



        val storesFragmnet = StoresFragmnet()

        val reservationsFragment = ReservationsFragment()

        val fragList = mutableListOf(storesFragmnet, reservationsFragment)

        var currentFragment: Fragment?=fragList[0]

        supportFragmentManager.beginTransaction().add(R.id.frameLayout, fragList[0]).commit()
        supportFragmentManager.beginTransaction().show(fragList[0]).commit()


        binding.bottomNavigation.setOnItemSelectedListener() { item ->
            val index = when (item.itemId) {
                R.id.home -> 0
                R.id.client -> 1
                else -> -1
            }
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
        setContentView(binding.root)
    }
}