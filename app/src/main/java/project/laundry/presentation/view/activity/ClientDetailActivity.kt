package project.laundry.presentation.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import project.laundry.data.dataclass.ClientData
import project.laundry.databinding.ActivityClientDetailBinding

class ClientDetailActivity : AppCompatActivity() {

    lateinit var binding : ActivityClientDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClientDetailBinding.inflate(layoutInflater)

        val intent = Intent()
        val client = intent.getSerializableExtra("client") as ClientData?

        setContentView(binding.root)
    }
}