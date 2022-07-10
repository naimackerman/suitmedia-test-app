package com.suitmedia.naim.suitmediatestapp.ui.firstScreen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.suitmedia.naim.suitmediatestapp.data.UserPreferences
import com.suitmedia.naim.suitmediatestapp.databinding.ActivityFirstScreenBinding
import com.suitmedia.naim.suitmediatestapp.ui.ViewModelFactory
import com.suitmedia.naim.suitmediatestapp.ui.secondScreen.SecondScreenActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class FirstScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstScreenBinding
    private lateinit var firstViewModel: FirstScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        firstViewModel = ViewModelProvider(
            this@FirstScreenActivity,
            ViewModelFactory(UserPreferences.getInstance(dataStore), this@FirstScreenActivity)
        )[FirstScreenViewModel::class.java]

        initAction()
    }

    private fun initAction() {
        binding.apply {
            firstBtnCheck.setOnClickListener {
                if (firstEtPalindrome.text.toString().isEmpty()) {
                    showDialog("Invalid", "Input sentence text first on Palindrome field")
                } else {
                    if (isPalindrome(firstEtPalindrome.text.toString().replace("\\s".toRegex(), ""))) {
                        showDialog("Result", "isPalindrome")
                    } else {
                        showDialog("Result", "not palindrome")
                    }
                }
            }
            firstBtnNext.setOnClickListener {
                if (firstEtName.text.toString().isEmpty()) {
                    showDialog("Invalid", "Input name first on Name field")
                } else {
                    startSecondScreen(firstEtName.text.toString())
                }
            }
        }
    }

    private fun startSecondScreen(name: String) {
        firstViewModel.saveUserName(name)
        val intent = Intent(this, SecondScreenActivity::class.java)
        startActivity(intent)
    }

    private fun showDialog(title: String, message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun isPalindrome(text: String): Boolean {
        val reverseString = text.reversed()
        return text.equals(reverseString, ignoreCase = true)
    }
}