package project.laundry.presentation.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import project.laundry.R
import project.laundry.data.App
import project.laundry.databinding.ActivityMainOwnerBinding
import project.laundry.presentation.view.fragments.OwReservationsFragment
import project.laundry.presentation.view.fragments.OwHomeFragment
import project.laundry.presentation.view.fragments.OwSalesFragment

class OwnerMainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainOwnerBinding
    val uid = App.prefs.uid!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        setContentView(binding.root)
    }
    private fun initView(){
        binding = ActivityMainOwnerBinding.inflate(layoutInflater)

        val owHomeFragment = OwHomeFragment()
        val owSalesFragment = OwSalesFragment()
        val owReservationsFragment = OwReservationsFragment()

        val fragList = mutableListOf(owHomeFragment, owSalesFragment, owReservationsFragment)

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
    }
}