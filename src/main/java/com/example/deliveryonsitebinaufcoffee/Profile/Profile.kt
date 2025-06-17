package com.example.deliveryonsitebinaufcoffee

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun ProfileScreen(
    onNavigateToChangePassword: () -> Unit = {},
    onNavigateToEditProfile: () -> Unit = {},
    onNavigateToFeedback: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    var isNotificationEnabled by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showShareDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .clipToBounds()
                .background(Color(0xFFF5F5F5))
                .padding(horizontal = 48.dp),
            verticalArrangement = Arrangement.spacedBy(23.dp),
            contentPadding = PaddingValues(top = 65.dp, bottom = 10.dp)
        ) {
            item {
                ProfileHeader(onEditProfile = onNavigateToEditProfile)
            }
            item {
                Spacer(modifier = Modifier.height(0.dp))
            }
            item {
                ProfileMenuItem(
                    title = "Change Password",
                    hasChevron = true,
                    onClick = onNavigateToChangePassword
                )
            }
            item {
                ProfileMenuItemWithToggle(
                    title = "Notification",
                    isToggled = isNotificationEnabled,
                    onToggleChange = { isNotificationEnabled = it }
                )
            }
            item {
                ProfileMenuItem(
                    title = "Share with Friends",
                    hasChevron = true,
                    onClick = {
                        showShareDialog = true
                    }
                )
            }

            item {
                ProfileMenuItem(
                    title = "Feedback",
                    hasChevron = true,
                    onClick = onNavigateToFeedback
                )
            }
            item {
                ProfileMenuItem(
                    title = "Log Out",
                    hasChevron = true,
                    onClick = {
                        showLogoutDialog = true
                    }
                )
            }
        }
        if (showShareDialog) {
            ShareWithFriendsDialog(
                onDismiss = { showShareDialog = false }
            )
        }
        if (showLogoutDialog) {
            LogoutConfirmationDialog(
                onConfirmLogout = {
                    showLogoutDialog = false
                    onLogout()
                },
                onDismiss = {
                    showLogoutDialog = false
                }
            )
        }
    }
}

@Composable
fun ShareWithFriendsDialog(
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable { onDismiss() }
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y= (-20).dp)
                .padding(0.dp)
                .border(
                    width = 9.dp,
                    color = Color(0xFFDBDBDB),
                    shape = RoundedCornerShape(
                        topStart = 18.dp,
                        topEnd = 18.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                ),
            shape = RoundedCornerShape(
                topStart = 18.dp,
                topEnd = 18.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top= 20.dp,start=20.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(24.dp)
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color(0xFFA08963)
                        )
                    }
                }

                Image(
                    painter = painterResource(id = R.drawable.friends),
                    contentDescription = "Friends",
                    modifier = Modifier
                        .offset(y = (-30).dp)
                        .fillMaxWidth(0.7f)
                        .height(150.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Text(
                    modifier = Modifier
                        .offset(y = (-35).dp),
                    text = "Invite Your Friends!",
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.lexend_bold)),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFA08963),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier
                        .offset(y = (-35).dp),
                    text = "Bin Auf Coffee has a super cool ordering\napplication, let's try it!",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.lexend_light)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF706D54),
                    textAlign = TextAlign.Center,
                    lineHeight = 21.sp
                )
                Spacer(modifier = Modifier.height(24.dp))
                Box(
                    modifier = Modifier

                        .offset(y= (-35).dp)
                        .shadow(elevation = 4.dp, spotColor = Color(0x40000000), ambientColor = Color(0x40000000))
                        .border(width = 1.dp, color = Color(0xFFDBDBDB), shape = RoundedCornerShape(size = 10.dp))
                        .width(326.dp)
                        .height(75.dp)
                        .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .padding(start=10.dp,end=10.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        SocialMediaButton(
                            drawableRes = R.drawable.whatsapp,
                            onClick = {
                                onDismiss()
                            }
                        )

                        SocialMediaButton(
                            drawableRes = R.drawable.instagram,
                            onClick = {
                                onDismiss()
                            }
                        )

                        SocialMediaButton(
                            drawableRes = R.drawable.twitter,
                            onClick = {
                                onDismiss()
                            }
                        )

                        SocialMediaButton(
                            drawableRes = R.drawable.line,
                            onClick = {
                                onDismiss()
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun SocialMediaButton(
    icon: ImageVector? = null,
    drawableRes: Int? = null,
    backgroundColor: Color = Color.Transparent,
    onClick: () -> Unit
) {
    when {
        drawableRes != null -> {
            IconButton(
                onClick = onClick,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    painter = painterResource(id = drawableRes),
                    contentDescription = null,
                    modifier = Modifier.size(54.dp),
                    tint = Color.Unspecified
                )
            }
        }
        icon != null -> {
            Button(
                onClick = onClick,
                modifier = Modifier.size(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = backgroundColor,
                    contentColor = Color.White
                ),
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun FeedbackScreen(
    onNavigateBack: () -> Unit = {}
) {
    var rating by remember { mutableIntStateOf(0) }
    var feedbackText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 40.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF706D54),
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // Main Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "We appreciate\nyour feedback!",
                fontSize = 28.sp,
                fontFamily = FontFamily(Font(R.font.lexend_bold)),
                fontWeight = FontWeight.Bold,
                color = Color(0xFF706D54),
                textAlign = TextAlign.Center,
                lineHeight = 35.sp
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Give us feedback as long as you use\nthis application or Alissa Tukhriya!",
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(R.font.lexend_light)),
                fontWeight = FontWeight(600),
                color = Color(0xFF706D54),
                textAlign = TextAlign.Center,
                lineHeight = 18.sp,
                letterSpacing = 0.26.sp,
            )

            Spacer(modifier = Modifier.height(25.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(1.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (i in 1..5) {
                    Icon(
                        imageVector = if (i <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = "Star $i",
                        tint = if (i <= rating) Color(0xFFC9B194) else Color(0xFFE0E0E0),
                        modifier = Modifier
                            .size(50.dp)
                            .clickable { rating = i }
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Card(
                modifier = Modifier
                    .width(284.dp)
                    .height(163.dp)
                    .border(width = 1.dp, color = Color(0xFFCFCFCF), shape = RoundedCornerShape(22.dp)),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                TextField(
                    value = feedbackText,
                    onValueChange = { feedbackText = it },
                    placeholder = {
                        Text(
                            text = "What can we do to improve your\nexperience?",
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.lexend_light)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF706D54),
                            lineHeight = 18.sp
                        )
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 4.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_light)),
                        color = Color(0xFF333333)
                    )
                )
            }

            Spacer(modifier = Modifier.height(35.dp))
            Button(
                onClick = {
                    onNavigateBack()
                },
                modifier = Modifier
                    .width(244.dp)
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF706D54),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(200.dp)
            ) {
                Text(
                    text = "Submit Feedback",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.lexend_bold)),
                    fontWeight = FontWeight(700),
                    textAlign = TextAlign.Center,
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun LogoutConfirmationDialog(
    onConfirmLogout: () -> Unit,
    onDismiss: () -> Unit,
    isLoading: Boolean = false
) {
    Dialog(onDismissRequest = { if (!isLoading) onDismiss() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isLoading) "Logging out..." else "Are you logging out?",
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.lexend_bold)),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFA08963),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (!isLoading) {
                    Text(
                        text = "You can always log back in at any time.",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_light)),
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF999999),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                if (!isLoading) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = onDismiss,
                            modifier = Modifier
                                .weight(1f)
                                .height(45.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFE0E0E0),
                                contentColor = Color(0xFF706D54)
                            ),
                            shape = RoundedCornerShape(25.dp)
                        ) {
                            Text(
                                text = "Cancel",
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.lexend_bold)),
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Button(
                            onClick = onConfirmLogout,
                            modifier = Modifier
                                .weight(1f)
                                .height(45.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF706D54),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(25.dp)
                        ) {
                            Text(
                                text = "Log Out",
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.lexend_bold)),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                } else {
                    CircularProgressIndicator(
                        modifier = Modifier.size(40.dp),
                        color = Color(0xFF706D54)
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileHeader(onEditProfile: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top=20.dp)
            .height(130.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFA08963),
                            Color(0xFF8B6F47)
                        )
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 9.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(200.dp),
                        ),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Color(0xFFD4A574),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.alis),
                            contentDescription = "Profile",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(20.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Spacer(modifier = Modifier.width(13.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {Column(
                    verticalArrangement = Arrangement.spacedBy((-5).dp)
                ) {
                    Text(
                        text = "Alissa Tukhriya",
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_bold)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFFFFFFFF),
                    )

                    Text(
                        text = "dmlalis10@gmail.com",
                        fontSize = 10.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_light)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFFFFFF),
                    )
                }

                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = onEditProfile,
                        modifier = Modifier
                            .height(31.dp)
                            .width(140.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6E6B4D),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(200.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "Edit profile",
                            fontSize = 15.sp,
                            fontFamily = FontFamily(Font(R.font.lexend_bold)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFFFFFFFF),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileMenuItem(
    title: String,
    hasChevron: Boolean = false,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .width(293.dp)
            .height(69.dp)
            .border(width = 4.dp, color = Color(0xFFDBDBDB), shape = RoundedCornerShape(20.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 15.dp, top = 20.dp, bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 10.dp),
                text = title,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.lexend_light)),
                fontWeight = FontWeight(1000),
                color = Color(0xFF706D54)
            )

            if (hasChevron) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Navigate",
                    tint = Color(0xFF706D54)
                )
            }
        }
    }
}

@Composable
fun ProfileMenuItemWithToggle(
    title: String,
    isToggled: Boolean,
    onToggleChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .width(293.dp)
            .height(69.dp)
            .border(width = 4.dp, color = Color(0xFFDBDBDB), shape = RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 15.dp, top = 20.dp, bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 10.dp),
                text = title,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.lexend_light)),
                fontWeight = FontWeight(1000),
                color = Color(0xFF706D54)
            )

            Switch(
                checked = isToggled,
                onCheckedChange = onToggleChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFF706D54),
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color(0xFFE0E0E0),
                    checkedBorderColor = Color.Transparent,
                    uncheckedBorderColor = Color.Transparent
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}

@Preview(showBackground = true)
@Composable
fun FeedbackScreenPreview() {
    FeedbackScreen()
}