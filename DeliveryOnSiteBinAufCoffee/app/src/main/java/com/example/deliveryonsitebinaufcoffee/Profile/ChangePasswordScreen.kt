package com.example.deliveryonsitebinaufcoffee

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.deliveryonsitebinaufcoffee.viewmodel.AccountItems

@Composable
fun ChangePasswordScreen(
    onNavigateBack: () -> Unit = {},
    accountViewModel: AccountItems = viewModel()
) {
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }

    // Observe change password state
    val changePasswordState by accountViewModel.changePasswordState.collectAsState()

    // Handle state changes
    LaunchedEffect(changePasswordState) {
        when (changePasswordState) {
            is AccountItems.AuthState.Success -> {
                showSuccessDialog = true
            }
            else -> {}
        }
    }

    // Clear form when success dialog is shown
    LaunchedEffect(showSuccessDialog) {
        if (showSuccessDialog) {
            newPassword = ""
            confirmPassword = ""
        }
    }

    // Success Dialog
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = {
                showSuccessDialog = false
                accountViewModel.clearChangePasswordState()
                onNavigateBack()
            },
            title = {
                Text(
                    text = "Berhasil!",
                    fontFamily = FontFamily(Font(R.font.lexend_bold)),
                    color = Color(0xFF706D54)
                )
            },
            text = {
                val currentState = changePasswordState
                Text(
                    text = if (currentState is AccountItems.AuthState.Success) {
                        currentState.message
                    } else {
                        "Password berhasil diubah"
                    },
                    fontFamily = FontFamily(Font(R.font.lexend_light)),
                    color = Color(0xFF706D54)
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSuccessDialog = false
                        accountViewModel.clearChangePasswordState()
                        onNavigateBack()
                    }
                ) {
                    Text(
                        "OK",
                        fontFamily = FontFamily(Font(R.font.lexend_bold)),
                        color = Color(0xFF8B7355)
                    )
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 60.dp, bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color(0xFF8B7355),
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onNavigateBack() }
            )
        }

        // Scrollable content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            item {
                Text(
                    modifier = Modifier.padding(top = 1.dp, start = 15.dp),
                    text = "Change Your\nPassword",
                    fontSize = 32.sp,
                    fontFamily = FontFamily(Font(R.font.lexend_bold)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF706D54),
                    textAlign = TextAlign.Start,
                    lineHeight = 40.sp
                )
            }

            // Show error message if any
            item {
                changePasswordState.let { state ->
                    if (state is AccountItems.AuthState.Error) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
                        ) {
                            Text(
                                text = state.message,
                                modifier = Modifier.padding(16.dp),
                                color = Color(0xFFD32F2F),
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.lexend_light))
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    text = "New Password",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.lexend_light)),
                    color = Color(0xFF706D54),
                    modifier = Modifier.padding(start = 15.dp, top = 30.dp),
                )
            }

            item {
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start=13.dp, top = 12.dp, bottom = 4.dp, end = 13.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFB8936D),
                        unfocusedBorderColor = Color(0xFF706D54),
                        focusedTextColor = Color(0xFF333333),
                        unfocusedTextColor = Color(0xFF333333)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    enabled = changePasswordState !is AccountItems.AuthState.Loading
                )
            }

            item {
                Text(
                    text = "Must be at least 8 characters",
                    fontSize = 11.sp,
                    fontFamily = FontFamily(Font(R.font.lexend_light)),
                    color = if (newPassword.length >= 8 || newPassword.isEmpty()) Color(0xFF735557) else Color(0xFFD32F2F),
                    modifier = Modifier.padding(start = 15.dp, bottom = 20.dp),
                )
            }

            item {
                Text(
                    text = "Confirm Password",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.lexend_light)),
                    color = Color(0xFF706D54),
                    modifier = Modifier.padding(start = 15.dp),
                )
            }

            item {
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start=13.dp, top = 12.dp, bottom = 4.dp, end = 13.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFB8936D),
                        unfocusedBorderColor = Color(0xFF706D54),
                        focusedTextColor = Color(0xFF333333),
                        unfocusedTextColor = Color(0xFF333333)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    enabled = changePasswordState !is AccountItems.AuthState.Loading
                )
            }

            item {
                val passwordsMatch = confirmPassword.isEmpty() || newPassword == confirmPassword
                Text(
                    text = if (passwordsMatch) "Both passwords must match" else "Passwords do not match",
                    fontSize = 11.sp,
                    fontFamily = FontFamily(Font(R.font.lexend_light)),
                    color = if (passwordsMatch) Color(0xFF735557) else Color(0xFFD32F2F),
                    modifier = Modifier.padding(start = 15.dp, bottom = 30.dp),
                )
            }

            // Button
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            if (newPassword.isNotBlank() && confirmPassword.isNotBlank()) {
                                // Kirim email dan password baru ke API
                                accountViewModel.changePassword(
                                    newPassword = newPassword,
                                    confirmPassword = confirmPassword
                                )
                            }
                        },
                        modifier = Modifier
                            .width(257.dp)
                            .height(45.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFA08963),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(172.dp),
                        enabled = run {
                            val currentState = changePasswordState
                            currentState !is AccountItems.AuthState.Loading &&
                                    newPassword.isNotBlank() &&
                                    confirmPassword.isNotBlank() &&
                                    newPassword.length >= 8 &&
                                    newPassword == confirmPassword
                        }
                    ) {
                        val currentState = changePasswordState
                        if (currentState is AccountItems.AuthState.Loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = "SAVE NEW PASSWORD",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.lexend_bold)),
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}