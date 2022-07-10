package com.suitmedia.naim.suitmediatestapp.ui.thirdScreen

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.suitmedia.naim.suitmediatestapp.adapter.LoadingStateAdapter
import com.suitmedia.naim.suitmediatestapp.adapter.UserListAdapter
import com.suitmedia.naim.suitmediatestapp.data.UserPreferences
import com.suitmedia.naim.suitmediatestapp.databinding.ActivityThirdScreenBinding
import com.suitmedia.naim.suitmediatestapp.ui.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ThirdScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdScreenBinding
    private lateinit var thirdViewModel: ThirdScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        thirdViewModel = ViewModelProvider(
            this@ThirdScreenActivity,
            ViewModelFactory(UserPreferences.getInstance(dataStore), this@ThirdScreenActivity)
        )[ThirdScreenViewModel::class.java]

        val layoutManager = LinearLayoutManager(this)
        binding.thirdRvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.thirdRvUsers.addItemDecoration(itemDecoration)

        initAction()
        getData()
    }

    private fun getData() {
        val adapter = UserListAdapter { String ->
            thirdViewModel.saveSelectedUserName(String)
            finish()
        }
        adapter.addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.NotLoading && adapter.itemCount < 1) {
                binding.apply {
                    thirdTvEmpty.isVisible = true
                    thirdRvUsers.isVisible = false
                }
            } else {
                binding.apply {
                    thirdTvEmpty.isVisible = false
                    thirdRvUsers.isVisible = true
                }
            }
        }
        binding.thirdRvUsers.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        thirdViewModel.users.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }

    private fun initAction() {
        binding.apply {
            thirdToolbar.setNavigationOnClickListener {
                onBackPressed()
                finish()
            }
            thirdSwipeLayout.setOnRefreshListener {
                getData()
                thirdSwipeLayout.isRefreshing = false
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}