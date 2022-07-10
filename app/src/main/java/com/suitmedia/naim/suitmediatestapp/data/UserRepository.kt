package com.suitmedia.naim.suitmediatestapp.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.suitmedia.naim.suitmediatestapp.database.UserDatabase
import com.suitmedia.naim.suitmediatestapp.network.ApiService
import com.suitmedia.naim.suitmediatestapp.network.UserResponseItem

class UserRepository(private val userDatabase: UserDatabase, private val apiService: ApiService) {
    fun getUser(): LiveData<PagingData<UserResponseItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 4
            ),
            remoteMediator = UserRemoteMediator(userDatabase, apiService),
            pagingSourceFactory = {
                userDatabase.userDao().getAllUser()
//                UserPagingSource(apiService)
            }
        ).liveData
    }
}