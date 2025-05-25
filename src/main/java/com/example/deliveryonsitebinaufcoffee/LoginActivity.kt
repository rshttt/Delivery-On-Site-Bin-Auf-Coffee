package com.example.deliveryonsitebinaufcoffee

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var showGreeting by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(3000)
        showGreeting = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = showGreeting,
            enter = fadeIn(animationSpec = tween(500)),
            exit = fadeOut(animationSpec = tween(500))
        ) {
            Greeting()
        }

        AnimatedVisibility(
            visible = !showGreeting,
            enter = fadeIn(animationSpec = tween(500)),
            exit = fadeOut(animationSpec = tween(500))
        ) {
            LoginPage()
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Box(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.opening),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun LoginPage(modifier: Modifier = Modifier) {
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
        onClickButton = {
            context.startActivity(intent)
        },
        inputEmail = { newEmail -> email = newEmail },
        inputUsername = { newUsername -> username = newUsername },
        inputPassword = { newPassword -> password = newPassword },
        navigation = controller
    )
}

@Serializable
object SignUp
@Serializable
object SignIn

@Composable
fun Login(
    modifier: Modifier = Modifier,
    signUp: Boolean,
    email: String,
    username: String,
    password: String,
    onClickSignUp: () -> Unit,
    onClickSignIn: () -> Unit,
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
                .fillMaxSize()
                .padding(top = 80.dp),
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
                        SignUpMenu(email, username, password, onClickButton, inputEmail, inputUsername, inputPassword)
                    }
                    composable<SignIn> {
                        SignInMenu(email, password, onClickButton, inputEmail, inputPassword)
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
    onClickButton: () -> Unit,
    inputEmail: (String) -> Unit,
    inputUsername: (String) -> Unit,
    inputPassword: (String) -> Unit
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

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
            modifier = Modifier
                .width(268.dp)
        )

        Spacer(modifier = Modifier.height(0.dp))

        Button(
            onClick = onClickButton,
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
                text = "SIGN UP",
                fontFamily = FontFamily(Font(resId = R.font.lexend_bold)),
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun SignInMenu(
    email: String,
    password: String,
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
                modifier = Modifier
                    .width(268.dp)
            )

            Text(
                text = "Forgot your password?",
                fontFamily = FontFamily(Font(resId = R.font.lexend_bold)),
                fontSize = 12.sp,
                color = Color.Black.copy(alpha = 0.42f),
                modifier = Modifier
                    .clickable {  }
            )

            Spacer(modifier = Modifier.height(0.dp))

            Button(
                onClick = onClickButton,
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
        }

        Button(
            onClick = {},
            colors = ButtonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black.copy(alpha = 0.42f),
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Color.Transparent
            )
        ) {
            Row {
                Text(
                    text = "Don't have an account?",
                    fontFamily = FontFamily(Font(resId = R.font.lexend_light)),
                    fontSize = 12.sp
                )

                Text(
                    text = " Register here!",
                    fontFamily = FontFamily(Font(resId = R.font.lexend_bold)),
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        Greeting()
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    AppTheme {
        LoginPage()
    }
}
