package com.example.smspro.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TelegramCategoryTabs(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    val categories = listOf("همه", "شخصی", "بانکی", "تراکنش‌ها", "سایر")
    
    ScrollableTabRow(
        selectedTabIndex = selectedTab,
        edgePadding = 16.dp,
        backgroundColor = Color.White,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                color = Color(0xFF2481CC)
            )
        },
        divider = {}
    ) {
        categories.forEachIndexed { index, title ->
            Tab(
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                text = {
                    Text(
                        text = title,
                        color = if (selectedTab == index) Color(0xFF2481CC) else Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    }
}

@Composable
fun TelegramChatItem(
    name: String,
    lastMessage: String,
    time: String,
    unreadCount: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(Color(0xFFE0E0E0)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name.take(1),
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(
            modifier = Modifier.weight(1.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    text = time,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = lastMessage,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1.dp)
                )
                
                if (unreadCount > 0) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color(0xFF4EBA6F))
                            .padding(horizontal = 6.dp, vertical = 2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = unreadCount.toString(),
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MainSmsScreen() {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "جيبيتو",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2481CC)
                    )
                    
                    Row {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "جستجو",
                                tint = Color.DarkGray
                            )
                        }
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "منو",
                                tint = Color.DarkGray
                            )
                        }
                    }
                }

                TelegramCategoryTabs(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )
            }
        },
        backgroundColor = Color.White
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(10) { index ->
                TelegramChatItem(
                    name = "مخاطب یا شماره $index",
                    lastMessage = "این یک نمونه متن پیامک دریافتی است...",
                    time = "۱۲:۱۰ ب.ظ",
                    unreadCount = if (index % 2 == 0) 2 else 0,
                    onClick = { }
                )
            }
        }
    }
}
