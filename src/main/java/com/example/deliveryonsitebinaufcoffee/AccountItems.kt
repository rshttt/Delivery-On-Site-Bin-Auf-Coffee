package com.example.deliveryonsitebinaufcoffee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AccountItems: ViewModel() {
    data class Account(
        val email: String,
        val username: String,
        val password: String
    )

    private val _accounts = MutableStateFlow<List<Account>>(emptyList())

    init {
        setAccount()
    }

    private fun setAccount() {
        viewModelScope.launch {
            val accountItem = listOf(
                Account("dumpy@dumpy.com", "dumpy", "Admin123")
            )
            _accounts.value = accountItem
        }
    }

    fun registerAccount(
        email: String,
        username: String,
        password: String
    ) {
        val newAccount = Account(email, username, password)
        _accounts.value = _accounts.value.toMutableList().apply {
            add(newAccount)
        }
    }

    data class PublicAccount(val email: String, val username: String)
    fun loginAccount(
        email: String,
        password: String
    ): PublicAccount? {
        val getAccount = _accounts.value.find {
            it.email == email && it.password == password
        }
        return getAccount?.let {
            PublicAccount(getAccount.email, getAccount.username)
        }
    }
}