package com.example.waluty.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun MyShimmer(index: Int, size: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = if (index == 0) {
                    0.dp
                } else {
                    8.dp
                },
                bottom = if (index + 1 == size) {
                    16.dp
                } else {
                    8.dp
                }
            )
            .shimmer()
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(Color.Gray)
                .align(Alignment.CenterVertically),
            contentAlignment = Alignment.Center
        ) {
        }
        Spacer(modifier = Modifier.width(16.dp))
        Row(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray)
                        .height(20.dp)
                        .shimmer()
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray)
                        .height(20.dp)
                        .shimmer()
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
    }
}