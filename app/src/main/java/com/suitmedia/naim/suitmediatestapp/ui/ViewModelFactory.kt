package com.suitmedia.naim.suitmediatestapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.suitmedia.naim.suitmediatestapp.data.UserPreferences
import com.suitmedia.naim.suitmediatestapp.di.Injection
import com.suitmedia.naim.suitmediatestapp.ui.firstScreen.FirstScreenViewModel
import com.suitmedia.naim.suitmediatestapp.ui.secondScreen.SecondScreenViewModel
import com.suitmedia.naim.suitmediatestapp.ui.thirdScreen.ThirdScreenViewModel

class ViewModelFactory(private val pref: UserPreferences, private val context: Context) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FirstScreenViewModel::class.java) -> {
                FirstScreenViewModel(pref) as T
            }
            modelClass.isAssignableFrom(SecondScreenViewModel::class.java) -> {
                SecondScreenViewModel(pref) as T
            }
            modelClass.isAssignableFrom(ThirdScreenViewModel::class.java) -> {
                ThirdScreenViewModel(pref, Injection.provideRepository(context)) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
}