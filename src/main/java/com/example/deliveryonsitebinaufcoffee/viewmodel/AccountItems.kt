package com.example.deliveryonsitebinaufcoffee.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryonsitebinaufcoffee.API.RetrofitInstance
import com.example.deliveryonsitebinaufcoffee.model.LoginRequest
import com.example.deliveryonsitebinaufcoffee.model.RegistrationRequest
import com.example.deliveryonsitebinaufcoffee.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException
import androidx.core.content.edit
import com.example.deliveryonsitebinaufcoffee.model.ChangePasswordRequest

class AccountItems(application: Application) : AndroidViewModel(application) {

    // Updated PublicAccount dengan property name
    data class PublicAccount(
        val id: Int,
        val email: String,
        val name: String,        // Tambahkan property name
        val username: String,    // Tetap ada jika diperlukan
        val token: String
    )

    private val _userAccount = MutableStateFlow<PublicAccount?>(null)
    val userAccount: StateFlow<PublicAccount?> = _userAccount.asStateFlow()

    private val apiService = RetrofitInstance.api
    private val sharedPreferences = application.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    sealed class AuthState {
        object Idle : AuthState()
        object Loading : AuthState()
        data class Success(val message: String) : AuthState()
        data class Error(val message: String) : AuthState()
    }

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    // State untuk change password
    private val _changePasswordState = MutableStateFlow<AuthState>(AuthState.Idle)
    val changePasswordState: StateFlow<AuthState> = _changePasswordState.asStateFlow()

    // Tambahkan fungsi untuk check apakah user sudah login
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    init {
        // Check apakah user sudah login saat ViewModel dibuat
        checkLoginStatus()
    }

    fun loginAccount(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val loginRequest = LoginRequest(email, password)
                val response = apiService.login(loginRequest)

                saveAuthData(response.token, response.user)
                _authState.value = AuthState.Success(response.message)

            } catch (e: HttpException) {
                val errorMessage = if (e.code() == 401) {
                    "Email atau password salah."
                } else {
                    "Login gagal: ${e.message()}"
                }
                _authState.value = AuthState.Error(errorMessage)
            } catch (e: IOException) {
                _authState.value = AuthState.Error("Tidak ada koneksi internet. Silakan periksa kembali.")
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Terjadi kesalahan: ${e.message}")
            }
        }
    }

    fun registerAccount(email: String, username: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val registrationRequest = RegistrationRequest(
                    name = username,
                    email = email,
                    password = password,
                    passwordConfirmation = password
                )
                val response = apiService.register(registrationRequest)

                saveAuthData(response.token, response.user)
                _authState.value = AuthState.Success(response.message)

            } catch (e: HttpException) {
                _authState.value = AuthState.Error("Registrasi gagal: ${e.message()}")
            } catch (e: IOException) {
                _authState.value = AuthState.Error("Tidak ada koneksi internet. Silakan periksa kembali.")
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Terjadi kesalahan: ${e.message}")
            }
        }
    }

    // Fungsi untuk mengubah password
    fun changePassword(newPassword: String, confirmPassword: String) {
        viewModelScope.launch {
            _changePasswordState.value = AuthState.Loading

            // Validasi input di frontend
            if (newPassword != confirmPassword) {
                _changePasswordState.value = AuthState.Error("Password baru dan konfirmasi password tidak sama")
                return@launch
            }

            if (newPassword.length < 8) {
                _changePasswordState.value = AuthState.Error("Password baru harus minimal 8 karakter")
                return@launch
            }

            try {
                val token = getToken()
                val userEmail = getUserEmail()

                if (token == null || userEmail == null) {
                    _changePasswordState.value = AuthState.Error("Sesi login telah berakhir")
                    return@launch
                }

                // Request hanya berisi email dan password baru
                val request = ChangePasswordRequest(
                    email = userEmail,
                    password = newPassword,
                    password_confirmation = confirmPassword
                )

                println("Debug: Sending request:")
                println("Debug: Email: $userEmail")
                println("Debug: Password length: ${newPassword.length}")
                println("Debug: Token: Bearer ${token.take(20)}...")
                println("Debug: Request JSON: ${Json.encodeToString(request)}")

                val response = apiService.resetPassword(
                    token = "Bearer $token",
                    request = request
                )

                _changePasswordState.value = AuthState.Success(response.message)

            } catch (e: HttpException) {
                val errorBody = try {
                    e.response()?.errorBody()?.string()
                } catch (ex: Exception) {
                    null
                }

                println("Debug: HTTP Error Code = ${e.code()}")
                println("Debug: Error Body = $errorBody")
                println("Debug: Request sent - Email: ${getUserEmail()}, Password length: ${newPassword.length}")

                val errorMessage = when (e.code()) {
                    401 -> "Sesi login telah berakhir"
                    422 -> {
                        if (errorBody != null) {
                            when {
                                errorBody.contains("password") -> "Password tidak valid"
                                errorBody.contains("email") -> "Email tidak valid"
                                else -> "Validasi gagal: $errorBody"
                            }
                        } else {
                            "Data yang dimasukkan tidak valid"
                        }
                    }
                    400 -> "Request tidak valid"
                    else -> "Gagal mengubah password: ${e.message()}"
                }
                _changePasswordState.value = AuthState.Error(errorMessage)
            } catch (e: IOException) {
                _changePasswordState.value = AuthState.Error("Tidak ada koneksi internet. Silakan periksa kembali.")
            } catch (e: Exception) {
                println("Debug: Exception = ${e.message}")
                _changePasswordState.value = AuthState.Error("Terjadi kesalahan: ${e.message}")
            }
        }
    }

    // Fungsi yang hilang - saveAuthData
    private fun saveAuthData(token: String, user: User) {
        sharedPreferences.edit() {
            val userJson = Json.encodeToString(user)
            putString("token", token)
            putString("user", userJson)
        }

        // Update PublicAccount dengan property name
        _userAccount.value = PublicAccount(
            id = user.id,
            email = user.email,
            name = user.name,        // Menggunakan user.name untuk property name
            username = user.name,    // Tetap simpan sebagai username juga jika diperlukan
            token = token
        )

        _isLoggedIn.value = true
    }

    // Fungsi yang hilang - checkLoginStatus
    private fun checkLoginStatus() {
        val token = sharedPreferences.getString("token", null)
        val userJson = sharedPreferences.getString("user", null)

        if (token != null && userJson != null) {
            try {
                val user = Json.decodeFromString<User>(userJson)
                _userAccount.value = PublicAccount(
                    id = user.id,
                    email = user.email,
                    name = user.name,
                    username = user.name,
                    token = token
                )
                _isLoggedIn.value = true
            } catch (e: Exception) {
                // Jika ada error parsing, clear data
                clearAuthData()
            }
        }
    }

    fun logoutAccount(onLogoutComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                val token = getToken()
                if (token != null) {
                    // Call logout API dengan Bearer token
                    RetrofitInstance.api.logout("Bearer $token")
                }

                // Clear local data
                logout()

                // Callback untuk navigasi
                onLogoutComplete()

            } catch (e: Exception) {
                // Even if API call fails, still clear local data
                logout()
                onLogoutComplete()
            }
        }
    }

    fun logout() {
        clearAuthData()
    }

    private fun clearAuthData() {
        sharedPreferences.edit() { clear() }
        _userAccount.value = null
        _isLoggedIn.value = false
    }

    fun clearAuthState() {
        _authState.value = AuthState.Idle
    }

    fun clearChangePasswordState() {
        _changePasswordState.value = AuthState.Idle
    }

    fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }

    // Fungsi helper untuk mendapatkan data user
    fun getUserName(): String? {
        return _userAccount.value?.name
    }

    fun getUserEmail(): String? {
        return _userAccount.value?.email
    }

    fun getUserId(): Int? {
        return _userAccount.value?.id
    }
}