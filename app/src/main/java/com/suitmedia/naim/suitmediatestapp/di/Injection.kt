package com.suitmedia.naim.suitmediatestapp.di

import android.content.Context
import com.suitmedia.naim.suitmediatestapp.data.UserRepository
import com.suitmedia.naim.suitmediatestapp.database.UserDatabase
import com.suitmedia.naim.suitmediatestapp.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val database = UserDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return UserRepository(database, apiService)
    }
}