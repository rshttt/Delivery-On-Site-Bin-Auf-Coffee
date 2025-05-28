package com.example.deliveryonsitebinaufcoffee

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.deliveryonsitebinaufcoffee.ui.theme.AppTheme
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

fun isValidEmail(email: String): Boolean {
//    ^ — Awal string
//    [A-Za-z] — Karakter pertama harus huruf (besar atau kecil)
//    (.*) — Karakter apapun (0 atau lebih kali) setelah huruf pertama (boleh apa saja)
//    ([@]{1}) — Harus ada tepat satu karakter @
//    (.+) — Minimal satu karakter apapun setelah @ (bagian domain sebelum titik)
//    (\.) — Harus ada karakter titik .
//    (.+) — Minimal satu karakter apapun setelah titik (bagian domain setelah titik)
    val emailRegex = Regex("^[A-Za-z](.*)(@)(.+)(\\.)(.+)")
    return emailRegex.matches(email)
}

fun isValidPassword(password: String): Boolean {
//    ^ — Awal string
//    (?=.*[a-z]) — Harus ada minimal satu huruf kecil
//    (?=.*[A-Z]) — Harus ada minimal satu huruf besar
//    (?=.*\d) — Harus ada minimal satu digit/angka (0-9)
//    .{8,} — Total panjang password minimal 8 karakter (boleh lebih)
//    $ — Akhir string
    val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")
    return passwordRegex.matches(password)
}

fun isValidUsername(username: String): Boolean {
//    ^ — Awal string
//    [A-Za-z0-9_] — Karakter yang diperbolehkan hanya huruf besar, huruf kecil, angka, dan underscore _
//    {3,} — Panjang string minimal 3 karakter
//    $ — Akhir string
    val usernameRegex = Regex("^[A-Za-z0-9_]{3,}$")
    return usernameRegex.matches(username)
}

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    var showGreeting by rememberSaveable { mutableStateOf(false) }
    var showLogin by rememberSaveable { mutableStateOf(false) }
    var showImage by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        showGreeting = true
        delay(300)
        showImage = true
        delay(3000)
        showImage = false
        showGreeting = false
        showLogin = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = showGreeting,
            enter = fadeIn(animationSpec = tween(500)),
            exit = fadeOut(animationSpec = tween(1000))
        ) {
            Greeting(showImage = showImage)
        }

        AnimatedVisibility(
            visible = showLogin,
            enter = fadeIn(animationSpec = tween(1000)),
            exit = fadeOut(animationSpec = tween(500))
        ) {
            LoginPage(changePage = { showLogin = false })
        }
    }
}

@Composable
fun Greeting(
    modifier: Modifier = Modifier,
    showImage: Boolean
) {
    Box(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = showImage,
            enter = fadeIn(animationSpec = tween(500)),
            exit = fadeOut(animationSpec = tween(500))
        ) {
            Image(
                painter = painterResource(id = R.drawable.opening),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}

@Serializable
object SignUp
@Serializable
object SignIn

@Composable
fun LoginPage(
    modifier: Modifier = Modifier,
    accounts: AccountItems = viewModel(),
    changePage: () -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var signUp by rememberSaveable { mutableStateOf(true) }
    val controller = rememberNavController()
    val context = LocalContext.current
    val intent = Intent(context, MainActivity::class.java)

    Login(
        email = email,
        signUp = signUp,
        username = username,
        password = password,
        onClickSignUp = {
            signUp = true
            controller.navigate(SignUp)
        },
        onClickSignIn = {
            signUp = false
            controller.navigate(SignIn)
        },
        onClickSignUpButton = {
            if(isValidEmail(email) && isValidPassword(password) && isValidUsername(username)) {
                accounts.registerAccount(email, username, password)
                context.startActivity(intent)
            } else
                Toast.makeText(context, "Registrasi akun gagal", Toast.LENGTH_SHORT).show()
        },
        onClickSignInButton = {
            if(isValidEmail(email) && isValidPassword(password)) {
                accounts.loginAccount(email, password)?.let {
                    context.startActivity(intent)
                } ?: run {
                    Toast.makeText(context, "Akun atau Password tidak sesuai", Toast.LENGTH_SHORT).show()
                }
            } else
                Toast.makeText(context, "Login akun gagal", Toast.LENGTH_SHORT).show()
        },
        onClickButton = {
            changePage()
            Toast.makeText(context, "Login berhasil", Toast.LENGTH_SHORT).show()
            context.startActivity(intent)
        },
        inputEmail = { newEmail -> email = newEmail },
        inputUsername = { newUsername -> username = newUsername },
        inputPassword = { newPassword -> password = newPassword },
        navigation = controller
    )
}

@Composable
fun Login(
    modifier: Modifier = Modifier,
    signUp: Boolean,
    email: String,
    username: String,
    password: String,
    onClickSignUp: () -> Unit,
    onClickSignIn: () -> Unit,
    onClickSignUpButton: () -> Unit,
    onClickSignInButton: () -> Unit,
    onClickButton: () -> Unit,
    inputEmail: (String) -> Unit,
    inputUsername: (String) -> Unit,
    inputPassword: (String) -> Unit,
    navigation: NavHostController
) {
    Box (
        modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .size(width = 124.dp, height = 155.dp)
            )

            Text(
                text = "Bin Auf Coffee",
                color = MaterialTheme.colorScheme.outline,
                fontSize = 32.sp,
                fontFamily = FontFamily(Font(resId = R.font.lemon_reg))
            )

            Spacer(modifier.height(40.dp))

            Row {
                Button(
                    onClick = onClickSignUp,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = if(!signUp) MaterialTheme.colorScheme.tertiary.copy(0.3f) else MaterialTheme.colorScheme.tertiary
                    ),
                    shape = RectangleShape,
                    elevation = null,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Sign Up",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(resId = R.font.lexend_light)),
                            modifier = Modifier
                                .padding(bottom = 12.dp)
                        )

                        HorizontalDivider(
                            modifier.width(124.dp),
                            thickness = 1.dp,
                            color = LocalContentColor.current
                        )
                    }
                }

                Button(
                    onClick = onClickSignIn,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = if(signUp) MaterialTheme.colorScheme.tertiary.copy(0.3f) else MaterialTheme.colorScheme.tertiary
                    ),
                    shape = RectangleShape,
                    elevation = null,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Sign In",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(resId = R.font.lexend_light)),
                            modifier = Modifier
                                .padding(bottom = 12.dp)
                        )

                        HorizontalDivider(
                            modifier.width(124.dp),
                            thickness = 1.dp,
                            color = LocalContentColor.current
                        )
                    }
                }
            }

            Spacer(modifier.height(20.dp))

            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NavHost(
                    navController = navigation,
                    startDestination = SignUp,
                    modifier = modifier
                ) {
                    composable<SignUp> {
                        SignUpMenu(email, username, password,onClickSignIn, onClickSignUpButton, inputEmail, inputUsername, inputPassword)
                    }
                    composable<SignIn> {
                        SignInMenu(email, password, onClickSignUp, onClickSignInButton, onClickButton, inputEmail, inputPassword)
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .wrapContentSize()
                .height(180.dp)
                .align(alignment = Alignment.BottomStart)
        ) {
            BoxWithConstraints {
                val screenWidth = maxWidth
                Image(
                    painter = painterResource(id = R.drawable.left_ellipse),
                    contentDescription = null,
                    modifier = Modifier
                        .width(screenWidth)
                        .aspectRatio(1f)
                )
            }
        }

        Box(
            modifier = Modifier
                .wrapContentSize()
                .height(152.dp)
                .align(alignment = Alignment.BottomEnd)
        ) {
            BoxWithConstraints {
                val screenWidth = maxWidth
                Image(
                    painter = painterResource(id = R.drawable.right_ellipse),
                    contentDescription = null,
                    modifier = Modifier
                        .width(screenWidth)
                        .aspectRatio(1f)
                )
            }
        }
    }
}

@Composable
fun SignUpMenu(
    email: String,
    username: String,
    password: String,
    onClickSignIn: () -> Unit,
    onClickSignUpButton: () -> Unit,
    inputEmail: (String) -> Unit,
    inputUsername: (String) -> Unit,
    inputPassword: (String) -> Unit
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column (
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = inputEmail,
                placeholder = {
                    Text(
                        text = "Email",
                        color = Color(0xFF735557).copy(alpha = 0.3f)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.outline,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                ),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                modifier = Modifier
                    .width(268.dp)
            )

            OutlinedTextField(
                value = username,
                onValueChange = inputUsername,
                placeholder = {
                    Text(
                        text = "Username",
                        color = Color(0xFF735557).copy(alpha = 0.3f)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.outline,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),

                    ),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                modifier = Modifier
                    .width(268.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = inputPassword,
                placeholder = {
                    Text(
                        text = "Password",
                        color = Color(0xFF735557).copy(alpha = 0.3f)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.outline,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),

                    ),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = { passwordVisible = !passwordVisible },
                        modifier = Modifier
                            .size(20.dp)
                    ) {
                        Icon(
                            imageVector = if(passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                modifier = Modifier
                    .width(268.dp)
            )

            Button(
                onClick = onClickSignUpButton,
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = Color.Transparent
                ),
                modifier = Modifier
                    .padding(top = 20.dp)
                    .size(height = 44.dp, width = 212.dp)
            ) {
                Text(
                    text = "SIGN UP",
                    fontFamily = FontFamily(Font(resId = R.font.lexend_bold)),
                    fontSize = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Already have an account?",
            fontFamily = FontFamily(Font(resId = R.font.lexend_light)),
            fontSize = 12.sp,
            color =  Color.Black.copy(alpha = 0.42f),
            modifier = Modifier
                .clickable { onClickSignIn() }
        )
    }
}

@Composable
fun SignInMenu(
    email: String,
    password: String,
    onClickSignUp: () -> Unit,
    onClickSignInButton: () -> Unit,
    onClickButton: () -> Unit,
    inputEmail: (String) -> Unit,
    inputPassword: (String) -> Unit
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column (
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = inputEmail,
                placeholder = {
                    Text(
                        text = "Email",
                        color = Color(0xFF735557).copy(alpha = 0.3f)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.outline,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),

                    ),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                modifier = Modifier
                    .width(268.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = inputPassword,
                placeholder = {
                    Text(
                        text = "Password",
                        color = Color(0xFF735557).copy(alpha = 0.3f)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.outline,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),

                    ),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = { passwordVisible = !passwordVisible },
                        modifier = Modifier
                            .size(20.dp)
                    ) {
                        Icon(
                            imageVector = if(passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                modifier = Modifier
                    .width(268.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Forgot your password?",
            fontFamily = FontFamily(Font(resId = R.font.lexend_bold)),
            fontSize = 12.sp,
            color = Color.Black.copy(alpha = 0.42f),
            modifier = Modifier
                .clickable {  }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onClickSignInButton,
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = Color.White,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Color.Transparent
            ),
            modifier = Modifier
                .size(height = 44.dp, width = 212.dp)
        ) {
            Text(
                text = "SIGN IN",
                fontFamily = FontFamily(Font(resId = R.font.lexend_bold)),
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row (
            modifier = Modifier
                .clickable { onClickSignUp() }
        ) {
            Text(
                text = "Don't have an account?",
                fontFamily = FontFamily(Font(resId = R.font.lexend_light)),
                fontSize = 12.sp,
                color = Color.Black.copy(alpha = 0.42f)
            )

            Text(
                text = " Register here!",
                fontFamily = FontFamily(Font(resId = R.font.lexend_bold)),
                fontSize = 12.sp,
                color = Color.Black.copy(alpha = 0.42f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row (
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier
                    .width(60.dp),
                color = Color.Black.copy(alpha = 0.3f)
            )

            Text(
                text = "or sign-in with",
                color = Color.Black.copy(alpha = 0.5f)

            )

            HorizontalDivider(
                modifier = Modifier
                    .width(60.dp),
                color = Color.Black.copy(alpha = 0.3f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(id = R.drawable.sign_in_google),
            contentDescription = null,
            modifier = Modifier
                .clip(shape = CircleShape)
                .clickable { onClickButton() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        Greeting(showImage = true)
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    AppTheme {
        LoginPage(changePage = {})
    }
}
