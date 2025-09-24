package com.yudha.listuser.ui.main

import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.yudha.listuser.R
import com.yudha.listuser.databinding.ActivityMainBinding
import com.yudha.listuser.ui.adapter.UserAdapter
import com.yudha.listuser.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var mainViewModel: MainActivityViewModel
    private lateinit var userAdapter: UserAdapter

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun setupViews() {
        setupRecyclerView()
        setupViewModel()
        
        // Fetch users data
        mainViewModel.fetchUsers()
    }

    override fun observeData() {
        observeViewModel()
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter()
        binding.recyclerViewUsers.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
    }

    private fun observeViewModel() {
        mainViewModel.users.observe(this) { users ->
            userAdapter.submitList(users)
        }

        mainViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.recyclerViewUsers.visibility = if (isLoading) View.GONE else View.VISIBLE
        }

        mainViewModel.errorMessage.observe(this) { error ->
            error?.let {
                showErrorSnackbar(it)
                mainViewModel.clearError()
            }
        }
    }

    private fun showErrorSnackbar(message: String) {
        val (displayMessage, duration) = when {
            message.contains("internet", ignoreCase = true) || message.contains("koneksi", ignoreCase = true) -> {
                message to Snackbar.LENGTH_INDEFINITE
            }
            message.contains("server", ignoreCase = true) -> {
                message to Snackbar.LENGTH_LONG
            }
            message.contains("timeout", ignoreCase = true) -> {
                message to Snackbar.LENGTH_LONG
            }
            message.contains("data", ignoreCase = true) || message.contains("parsing", ignoreCase = true) -> {
                message to Snackbar.LENGTH_LONG
            }
            else -> {
                message to Snackbar.LENGTH_LONG
            }
        }
        
        val snackbar = Snackbar.make(binding.root, displayMessage, duration)
        
        // Customize snackbar based on error type
        when {
            message.contains("internet", ignoreCase = true) || message.contains("koneksi", ignoreCase = true) -> {
                snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.error_no_internet))
                snackbar.setActionTextColor(ContextCompat.getColor(this, android.R.color.white))
                snackbar.setAction("Periksa Koneksi") {
                     mainViewModel.fetchUsers()
                 }
            }
            message.contains("server", ignoreCase = true) -> {
                snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.error_server))
                snackbar.setActionTextColor(ContextCompat.getColor(this, android.R.color.white))
                snackbar.setAction("Coba Lagi") {
                     mainViewModel.fetchUsers()
                 }
            }
            message.contains("timeout", ignoreCase = true) -> {
                snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.error_timeout))
                snackbar.setActionTextColor(ContextCompat.getColor(this, android.R.color.white))
                snackbar.setAction("Ulangi") {
                     mainViewModel.fetchUsers()
                 }
            }
            message.contains("data", ignoreCase = true) || message.contains("parsing", ignoreCase = true) -> {
                snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.error_parse))
                snackbar.setActionTextColor(ContextCompat.getColor(this, android.R.color.white))
                snackbar.setAction("Muat Ulang") {
                     mainViewModel.fetchUsers()
                 }
            }
            else -> {
                snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.error_general))
                snackbar.setActionTextColor(ContextCompat.getColor(this, android.R.color.white))
                snackbar.setAction("Coba Lagi") {
                     mainViewModel.fetchUsers()
                 }
            }
        }
        
        snackbar.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}