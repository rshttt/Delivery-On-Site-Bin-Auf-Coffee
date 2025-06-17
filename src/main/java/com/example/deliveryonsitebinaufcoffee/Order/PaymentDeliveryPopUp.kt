package com.example.deliveryonsitebinaufcoffee

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DeliveryOptionPopup(
    onDismiss: () -> Unit,
    onTakeAway: () -> Unit,
    onDirectDelivery: () -> Unit,
    onScheduled: () -> Unit,
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
                .offset(y = (-40).dp)
                .padding(0.dp),
            shape = RoundedCornerShape(
                topStart = 58.dp,
                topEnd = 58.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            border = BorderStroke(
                width = 9.dp,
                color = Color(0xFF706D54)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(4.dp)
                        .background(
                            Color(0xFFE0E0E0),
                            RoundedCornerShape(2.dp)
                        )
                )

                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Delivery Option",
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.lexend_bold)),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF706D54),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))
                Box(
                    modifier = Modifier
                        .shadow(
                            elevation = 8.dp,
                            spotColor = Color.Black,
                            ambientColor = Color.Black,
                            shape = RoundedCornerShape(11.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = Color(0xFF706D54),
                            shape = RoundedCornerShape(11.dp)
                        )
                        .width(252.dp)
                        .height(46.dp)
                        .background(
                            color = Color(0xFFA08963),
                            shape = RoundedCornerShape(11.dp)
                        )
                        .clickable { onTakeAway() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Take Away",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_bold)),
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(17.dp))

                // Direct Delivery Button
                Box(
                    modifier = Modifier
                        .shadow(
                            elevation = 8.dp,
                            spotColor = Color.Black,
                            ambientColor = Color.Black,
                            shape = RoundedCornerShape(11.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = Color(0xFF706D54),
                            shape = RoundedCornerShape(11.dp)
                        )
                        .width(252.dp)
                        .height(46.dp)
                        .background(
                            color = Color(0xFFA08963),
                            shape = RoundedCornerShape(11.dp)
                        )
                        .clickable { onDirectDelivery() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Direct Delivery",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_bold)),
                        color = Color.White
                    )
                }


                Spacer(modifier = Modifier.height(17.dp))
                Box(
                    modifier = Modifier
                        .shadow(
                            elevation = 8.dp,
                            spotColor = Color.Black,
                            ambientColor = Color.Black,
                            shape = RoundedCornerShape(11.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = Color(0xFF706D54),
                            shape = RoundedCornerShape(11.dp)
                        )
                        .width(252.dp)
                        .height(46.dp)
                        .background(
                            color = Color(0xFFA08963),
                            shape = RoundedCornerShape(11.dp)
                        )
                        .clickable { onScheduled() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Scheduled",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_bold)),
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun PaymentMethodPopup(
    onDismiss: () -> Unit,
    onScanQR: () -> Unit
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
                .offset(y = (-40).dp)
                .padding(0.dp),
            shape = RoundedCornerShape(
                topStart = 58.dp,
                topEnd = 58.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            border = BorderStroke(
                width = 9.dp,
                color = Color(0xFF706D54)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(4.dp)
                        .background(
                            Color(0xFFE0E0E0),
                            RoundedCornerShape(2.dp)
                        )
                )

                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Payment Method",
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.lexend_bold)),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF706D54),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Image(
                    painter = painterResource(id = R.drawable.qr),
                    contentDescription = "QR Code",
                    modifier = Modifier
                        .shadow(
                            elevation = 8.dp,
                            spotColor = Color.Black,
                            ambientColor = Color.Black,
                            shape = RoundedCornerShape(11.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = Color(0xFF706D54),
                            shape = RoundedCornerShape(11.dp)
                        )
                        .width(252.dp)
                        .height(252.dp)
                        .background(
                            color = Color(0xFFA08963),
                            shape = RoundedCornerShape(11.dp)
                        )
                        .clickable { }
                        .clip(RoundedCornerShape(11.dp)),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}