package project.laundry.ui.customer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import project.laundry.R
import project.laundry.data.dataclass.LoginCuResponse
import project.laundry.data.dataclass.StoreListItems
import project.laundry.databinding.ActivityCustomerMainBinding
import project.laundry.ui.customer.fragments.MapFragment
import project.laundry.ui.customer.fragments.SettingFragment

class CustomerMainActivity : AppCompatActivity() {
    lateinit var binding : ActivityCustomerMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerMainBinding.inflate(layoutInflater)

        val intent = Intent()
        val loginRes = intent.getSerializableExtra("loginCu") as LoginCuResponse?



        Log.d("login", loginRes.toString())
        val fragList = mutableListOf(MapFragment(), SettingFragment())

        var currentFragment: Fragment?=fragList[0]
        val bundle = Bundle()
        bundle.putSerializable("login", loginRes)
        currentFragment?.arguments = bundle

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
                    val bundle = Bundle()
                    bundle.putSerializable("login", loginRes)
                    currentFragment?.arguments = bundle
                }
            }
            true
        }
        setContentView(binding.root)
    }
}