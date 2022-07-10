package com.suitmedia.naim.suitmediatestapp.ui.secondScreen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.suitmedia.naim.suitmediatestapp.data.UserPreferences
import com.suitmedia.naim.suitmediatestapp.databinding.ActivitySecondScreenBinding
import com.suitmedia.naim.suitmediatestapp.ui.ViewModelFactory
import com.suitmedia.naim.suitmediatestapp.ui.thirdScreen.ThirdScreenActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SecondScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondScreenBinding
    private lateinit var secondViewModel: SecondScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        secondViewModel = ViewModelProvider(
            this@SecondScreenActivity,
            ViewModelFactory(UserPreferences.getInstance(dataStore), this@SecondScreenActivity)
        )[SecondScreenViewModel::class.java]

        secondViewModel.getUserName().observe(this) {
            binding.apply {
                secondTvName.text = it.userName
                secondTvSelected.text = it.selectedUserName
            }
        }

        initAction()
    }

    private fun initAction() {
        binding.apply {
            secondToolbar.setNavigationOnClickListener {
                onBackPressed()
            }
            secondBtnChoose.setOnClickListener {
                startThirdScreen()
            }
        }

    }

    private fun startThirdScreen() {
        val intent = Intent(this, ThirdScreenActivity::class.java)
        startActivity(intent)
    }
}