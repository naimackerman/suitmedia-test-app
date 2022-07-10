package com.suitmedia.naim.suitmediatestapp.ui.secondScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.suitmedia.naim.suitmediatestapp.data.User
import com.suitmedia.naim.suitmediatestapp.data.UserPreferences

class SecondScreenViewModel(private val pref: UserPreferences) : ViewModel() {
    fun getUserName(): LiveData<User> {
        return pref.getUserName().asLiveData()
    }
}