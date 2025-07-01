package com.example.deliveryonsitebinaufcoffee

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    onNavigateBack: () -> Unit = {}
) {
    var name by remember { mutableStateOf("Alissa Tukhriya") }
    var email by remember { mutableStateOf("dmlalis10@gmail.com") }
    var phoneNumber by remember { mutableStateOf("0823 2111 2410") }
    var birthday by remember { mutableStateOf("10/01/2006") }
    var showBottomSheet by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top=50.dp, start = 25.dp,end=25.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF8B6F47)
                    )
                }

                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Save",
                        tint = Color(0xFF4CAF50)
                    )
                }
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Edit Profile",
                    fontSize = 28.sp,
                    fontFamily = FontFamily(Font(R.font.lexend_bold)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF706D54)
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                contentPadding = PaddingValues(vertical = 24.dp)
            ) {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier.size(171.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(171.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF706D54))
                                    .align(Alignment.Center),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.alis),
                                    contentDescription = "Profile Picture",
                                    modifier = Modifier
                                        .size(166.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .size(39.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .border(2.dp, Color(0xFFE0E0E0), CircleShape)
                                    .align(Alignment.BottomEnd)
                                    .clickable { showBottomSheet = true },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit Photo",
                                    tint = Color(0xFF8B6F47),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
                item {
                    EditProfileField(
                        label = "Name",
                        value = name,
                        onValueChange = { name = it }
                    )
                }

                item {
                    EditProfileField(
                        label = "Email Address",
                        value = email,
                        onValueChange = { email = it },
                        keyboardType = KeyboardType.Email
                    )
                }

                item {
                    EditProfileField(
                        label = "Phone Number",
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        keyboardType = KeyboardType.Phone
                    )
                }

                item {
                    EditProfileField(
                        label = "Birthday",
                        value = birthday,
                        onValueChange = { birthday = it }
                    )
                }
            }
        }
        if (showBottomSheet) {
            BottomSheetDialog(
                onDismiss = { showBottomSheet = false },
                onCameraClick = {
                    showBottomSheet = false
                },
                onGalleryClick = {
                    showBottomSheet = false
                },
                onDeleteClick = {
                    showBottomSheet = false
                }
            )
        }
    }
}

@Composable
fun BottomSheetDialog(
    onDismiss: () -> Unit,
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit,
    onDeleteClick: () -> Unit
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
                    .offset(y= (-10).dp)
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
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
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

                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier
                            .offset(y= (-20).dp)
                            .padding(start=5.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BottomSheetItem(
                            icon = Icons.Default.CameraAlt,
                            label = "Camera",
                            backgroundColor = Color(0xFFA08963),
                            onClick = onCameraClick
                        )

                        BottomSheetItem(
                            icon = Icons.Default.Photo,
                            label = "Gallery",
                            backgroundColor = Color(0xFFA08963),
                            onClick = onGalleryClick
                        )

                        BottomSheetItem(
                            icon = Icons.Default.Delete,
                            label = "Delete",
                            backgroundColor = Color(0xFFA08963),
                            onClick = onDeleteClick
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

}

@Composable
fun BottomSheetItem(
    icon: ImageVector,
    label: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(68.dp)
                .clip(CircleShape)
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }

        Spacer(modifier = Modifier.height(1.dp))

        Text(
            text = label,
            fontSize = 13.sp,
            fontFamily = FontFamily(Font(R.font.lexend_light)),
            fontWeight = FontWeight(600),
            color = Color(0xFFA08963),
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.lexend_bold)),
            fontWeight = FontWeight(600),
            color = Color(0xFFA08963),
            modifier = Modifier.padding(start = 30.dp, bottom = 8.dp)
        )

        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp)
                .height(49.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFE8E8E8),
                unfocusedContainerColor = Color(0xFFE8E8E8),
                disabledContainerColor = Color(0xFFE8E8E8),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedTextColor = Color(0xFF333333),
                unfocusedTextColor = Color(0xFF666666),
                cursorColor = Color(0xFF8B6F47)
            ),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 13.sp,
                color = Color(0xFFA08963),
                fontFamily = FontFamily(Font(R.font.lexend_light)),
                fontWeight = FontWeight(400)
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    EditProfileScreen()
}