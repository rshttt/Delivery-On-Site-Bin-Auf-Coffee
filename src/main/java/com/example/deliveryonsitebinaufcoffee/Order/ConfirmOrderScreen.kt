package com.example.deliveryonsitebinaufcoffee

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import com.example.deliveryonsitebinaufcoffee.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmOrderScreen(
    cartItems: List<CartItem>,
    onBackClick: () -> Unit,
    onProceedToPay: () -> Unit,
    onUpdateQuantity: (CartItem, Int) -> Unit,
    onEditItem: (CartItem) -> Unit,
    onDeleteItem: (CartItem) -> Unit,
    onNavigateToPickupTime: () -> Unit,
    selectedPickupDay: String = "",
    selectedPickupTime: String = "",
    userId: Int, // Add userId parameter
    authToken: String, // Add authToken parameter
    orderViewModel: ProductViewModel = viewModel()
) {
    val context = LocalContext.current

    // Observe order submission state
    val isSubmittingOrder by orderViewModel.isSubmittingOrder.collectAsState()
    val orderSubmissionResult by orderViewModel.orderSubmissionResult.collectAsState()
    val errorMessage by orderViewModel.errorMessage.collectAsState()

    var additionalNotes by remember { mutableStateOf("") }
    var selectedDeliveryOption by remember {
        mutableStateOf(
            if (selectedPickupDay.isNotEmpty() && selectedPickupTime.isNotEmpty()) {
                "Scheduled - $selectedPickupDay, $selectedPickupTime"
            } else {
                "See all"
            }
        )
    }
    var selectedPaymentMethod by remember { mutableStateOf("QRIS") }
    var showDeliveryOptions by remember { mutableStateOf(false) }
    var showPaymentMethod by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf<CartItem?>(null) }

    val totalItems = cartItems.sumOf { it.quantity }
    val subtotal = cartItems.sumOf { it.totalPrice }

    // Handle order submission result
    LaunchedEffect(orderSubmissionResult) {
        orderSubmissionResult?.let { result ->
            if (result.success) {
                Toast.makeText(context, "Order submitted successfully!", Toast.LENGTH_LONG).show()
                onProceedToPay() // Navigate to payment or success screen
            }
            orderViewModel.clearOrderResult()
        }
    }

    // Handle error messages
    LaunchedEffect(errorMessage) {
        errorMessage?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            orderViewModel.clearOrderResult()
        }
    }

    showDeleteDialog?.let { itemToDelete ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = {
                Text(
                    text = "Hapus Item",
                    fontFamily = FontFamily(Font(R.font.lexend_bold)),
                    color = Color(0xFF706D54)
                )
            },
            text = {
                Text(
                    text = "Apakah Anda yakin ingin menghapus ${itemToDelete.product.name} dari pesanan?",
                    fontFamily = FontFamily(Font(R.font.lexend_light)),
                    color = Color(0xFFA08963)
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteItem(itemToDelete)
                        showDeleteDialog = null
                    }
                ) {
                    Text(
                        text = "Hapus",
                        color = Color(0xFFD32F2F),
                        fontFamily = FontFamily(Font(R.font.lexend_bold))
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = null }
                ) {
                    Text(
                        text = "Batal",
                        color = Color(0xFF706D54),
                        fontFamily = FontFamily(Font(R.font.lexend_light))
                    )
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(16.dp)
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(start = 35.dp, end = 35.dp, top = 50.dp, bottom = 60.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(x = (-20).dp)
                    .padding(bottom = 18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF706D54)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Fit
                )
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                item {
                    Text(
                        text = "CONFIRM\nORDER",
                        fontSize = 32.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_bold)),
                        color = Color(0xFF706D54),
                        lineHeight = 36.sp,
                        modifier = Modifier.padding(start = 10.dp, bottom = 2.dp)
                    )
                    Text(
                        text = "$totalItems items",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_bold)),
                        color = Color(0xFFA08963),
                        modifier = Modifier.padding(start = 10.dp, bottom = 20.dp)
                    )
                }

                items(cartItems) { cartItem ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 25.dp)
                            .border(
                                width = 5.dp,
                                color = Color(0xFFD9D9D9),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(16.dp)
                            ),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            AsyncImage(
                                model = cartItem.product.image_path,
                                contentDescription = cartItem.product.name,
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop,
                            )
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .offset(y = 5.dp)
                            ) {
                                Text(
                                    text = cartItem.product.name,
                                    fontSize = 18.sp,
                                    fontFamily = FontFamily(Font(R.font.lexend_bold)),
                                    color = Color(0xFFA08963)
                                )

                                Text(
                                    text = "Rp${String.format("%,d", cartItem.product.price).replace(",", ".")}",
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily(Font(R.font.lexend_light)),
                                    fontWeight = FontWeight(1000),
                                    color = Color(0xFFA08963),
                                    modifier = Modifier.padding(top = 0.dp)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .background(
                                                if (cartItem.quantity > 1) Color(0xFFD9D9D9) else Color(0xFFE0E0E0),
                                                CircleShape
                                            )
                                            .clickable {
                                                if (cartItem.quantity > 1) {
                                                    onUpdateQuantity(cartItem, cartItem.quantity - 1)
                                                }
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "-",
                                            style = TextStyle(
                                                fontSize = 16.sp,
                                                fontFamily = FontFamily(Font(R.font.lexend_light)),
                                                fontWeight = FontWeight(1000),
                                                color = if (cartItem.quantity > 1) Color(0xFFA08963) else Color(0xFFB8B5A1),
                                                textAlign = TextAlign.Center,
                                            )
                                        )
                                    }
                                    Text(
                                        text = cartItem.quantity.toString(),
                                        fontSize = 14.sp,
                                        fontFamily = FontFamily(Font(R.font.lexend_light)),
                                        color = Color(0xFFA08963),
                                        modifier = Modifier
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .background(
                                                Color(0xFFD9D9D9),
                                                CircleShape
                                            )
                                            .clickable {
                                                onUpdateQuantity(cartItem, cartItem.quantity + 1)
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "+",
                                            style = TextStyle(
                                                fontSize = 16.sp,
                                                fontFamily = FontFamily(Font(R.font.lexend_light)),
                                                fontWeight = FontWeight(1000),
                                                color = Color(0xFFA08963),
                                                textAlign = TextAlign.Center,
                                            )
                                        )
                                    }
                                }

                                Row(
                                    modifier = Modifier.padding(top = 4.dp)
                                ) {
                                    Text(
                                        text = "Size Option   : ${cartItem.size}",
                                        fontSize = 8.sp,
                                        fontFamily = FontFamily(Font(R.font.lexend_light)),
                                        fontWeight = FontWeight(1000),
                                        color = Color(0xFFA08963)
                                    )
                                }
                                Text(
                                    modifier = Modifier.offset(y = (-4).dp),
                                    text = "Level Sugar   : ${cartItem.sugar}",
                                    fontSize = 8.sp,
                                    fontFamily = FontFamily(Font(R.font.lexend_light)),
                                    fontWeight = FontWeight(1000),
                                    color = Color(0xFFA08963)
                                )
                                Text(
                                    modifier = Modifier.offset(y = (-8).dp),
                                    text = "Level Ice       : ${cartItem.ice}",
                                    fontSize = 8.sp,
                                    fontFamily = FontFamily(Font(R.font.lexend_light)),
                                    fontWeight = FontWeight(1000),
                                    color = Color(0xFFA08963)
                                )
                            }
                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                IconButton(
                                    onClick = { onEditItem(cartItem) },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit",
                                        tint = Color(0xFFB8B5A1),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                IconButton(
                                    onClick = { showDeleteDialog = cartItem },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete",
                                        tint = Color(0xFFD32F2F),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Additional Notes:",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_light)),
                        color = Color(0xFFA08963),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    OutlinedTextField(
                        value = additionalNotes,
                        onValueChange = { additionalNotes = it },
                        placeholder = {
                            Text(
                                text = "Leave notes...",
                                color = Color(0xFFA08963),
                                fontFamily = FontFamily(Font(R.font.lexend_light))
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xFFE0E0E0),
                            focusedBorderColor = Color(0xFF706D54),
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        )
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(1.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Delivery Option",
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.lexend_light)),
                            color = Color(0xFFA08963)
                        )
                        TextButton(
                            onClick = { showDeliveryOptions = true }
                        ) {
                            Text(
                                text = selectedDeliveryOption,
                                fontSize = 10.sp,
                                fontFamily = FontFamily(Font(R.font.lexend_light)),
                                color = Color(0xFFA08963)
                            )
                        }
                    }
                }
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Payment Method",
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.lexend_bold)),
                            color = Color(0xFFA08963)
                        )

                        TextButton(
                            onClick = { showPaymentMethod = true }
                        ) {
                            Text(
                                text = selectedPaymentMethod,
                                fontSize = 10.sp,
                                fontFamily = FontFamily(Font(R.font.lexend_light)),
                                color = Color(0xFFA08963)
                            )
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 2.dp,
                                color = Color(0xFFE0E0E0),
                                shape = RoundedCornerShape(20.dp)
                            ),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(13.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Subtotal",
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.lexend_bold)),
                                color = Color(0xFFA08963)
                            )

                            Text(
                                text = "Rp${String.format("%,d", subtotal).replace(",", ".")}",
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.lexend_light)),
                                fontWeight = FontWeight(1000),
                                color = Color(0xFFA08963)
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            // Submit order when button is clicked
                            orderViewModel.submitOrder(
                                cartItems = cartItems,
                                userId = userId,
                                additionalNotes = additionalNotes,
                                deliveryOption = selectedDeliveryOption,
                                paymentMethod = selectedPaymentMethod,
                                pickupDay = if (selectedDeliveryOption.contains("Scheduled")) selectedPickupDay else null,
                                pickupTime = if (selectedDeliveryOption.contains("Scheduled")) selectedPickupTime else null,
                                authToken = authToken
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF706D54)
                        ),
                        shape = RoundedCornerShape(28.dp),
                        enabled = cartItems.isNotEmpty() && !isSubmittingOrder
                    ) {
                        if (isSubmittingOrder) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = "Proceed to Pay",
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.lexend_bold)),
                                fontWeight = FontWeight(700),
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        if (showDeliveryOptions) {
            DeliveryOptionPopup(
                onDismiss = { showDeliveryOptions = false },
                onTakeAway = {
                    selectedDeliveryOption = "Take Away"
                    showDeliveryOptions = false
                },
                onDirectDelivery = {
                    selectedDeliveryOption = "Direct Delivery"
                    showDeliveryOptions = false
                },
                onScheduled = {
                    showDeliveryOptions = false
                    onNavigateToPickupTime()
                }
            )
        }
        if (showPaymentMethod) {
            PaymentMethodPopup(
                onDismiss = { showPaymentMethod = false },
                onScanQR = {
                    selectedPaymentMethod = "Scan QR"
                    showPaymentMethod = false
                }
            )
        }
    }
}