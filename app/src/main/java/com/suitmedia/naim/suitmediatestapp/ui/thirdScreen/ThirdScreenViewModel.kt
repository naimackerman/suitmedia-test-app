package com.suitmedia.naim.suitmediatestapp.ui.thirdScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.suitmedia.naim.suitmediatestapp.data.UserPreferences
import com.suitmedia.naim.suitmediatestapp.data.UserRepository
import com.suitmedia.naim.suitmediatestapp.network.UserResponseItem
import kotlinx.coroutines.launch

class ThirdScreenViewModel(private val pref: UserPreferences, userRepository: UserRepository) :
    ViewModel() {
    val users: LiveData<PagingData<UserResponseItem>> =
        userRepository.getUser().cachedIn(viewModelScope)

    fun saveSelectedUserName(selectedUserName: String) {
        viewModelScope.launch {
            pref.saveSelectedUserName(selectedUserName)
        }
    }
}