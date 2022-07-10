package com.suitmedia.naim.suitmediatestapp.ui.firstScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suitmedia.naim.suitmediatestapp.data.UserPreferences
import kotlinx.coroutines.launch

class FirstScreenViewModel(private val pref: UserPreferences) : ViewModel() {
    fun saveUserName(userName: String) {
        viewModelScope.launch {
            pref.saveUserName(userName)
        }
    }
}