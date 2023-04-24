package project.laundry.presentation.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import project.laundry.R
import project.laundry.databinding.ActivityMainBinding
import project.laundry.presentation.view.fragments.LaundryListFragment
import project.laundry.presentation.view.fragments.HomeFragment
import project.laundry.presentation.view.fragments.SalesFragment

class OwnerMainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var uid : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val fragList = mutableListOf(HomeFragment(), SalesFragment(), LaundryListFragment())
        supportFragmentManager.beginTransaction().add(R.id.frameLayout, fragList[0]).commit()
        supportFragmentManager.beginTransaction().show(fragList[0]).commit()
        var currentFragment: Fragment?=fragList[0]

        binding.bottomNavigation.setOnItemSelectedListener() { item ->
            val index = when (item.itemId) {
                R.id.home -> 0
                R.id.sales -> 1
                R.id.client -> 2
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
                }
            }
            true
        }
        setContentView(binding.root)
    }
}