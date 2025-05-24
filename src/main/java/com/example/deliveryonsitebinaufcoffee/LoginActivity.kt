package com.example.deliveryonsitebinaufcoffee

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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deliveryonsitebinaufcoffee.ui.theme.AppTheme
import kotlinx.coroutines.delay

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
            .background(color = MaterialTheme.colorScheme.tertiary),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo1),
            contentDescription = null,
            modifier = Modifier
                .padding(bottom = 80.dp)
                .size(width = 253.dp, height = 316.dp)
        )
    }
}

@Composable
fun LoginPage(modifier: Modifier = Modifier) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Login(
        email = email,
        password = password,
        inputEmail = { newEmail -> email = newEmail },
        inputPassword = { newPassword -> password = newPassword }
    )
}

@Composable
fun Login(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    inputEmail: (String) -> Unit,
    inputPassword: (String) -> Unit
) {
    Box (
        modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo1),
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
