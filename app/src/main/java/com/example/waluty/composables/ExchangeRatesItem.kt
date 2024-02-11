package com.example.waluty.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.waluty.data.exchangeRates.Rate
import com.example.waluty.extensions.toFormat

@Composable
fun ExchangeRatesItem(
    index: Int,
    rate: Rate,
    size: Int,
    onItemClick: ((Rate, Int) -> Unit)? = null,
    onIconClick: ((Rate, Boolean) -> Unit)? = null,
    favourite: Boolean
) {
    var favouriteRemembered by remember { mutableStateOf(favourite) }

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
            .clickable(enabled = onItemClick != null) {
                onItemClick?.invoke(rate, index)
            }
    ) {
        Spacer(modifier = Modifier.width(16.dp))

        // Circle label
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .align(Alignment.CenterVertically),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = rate.mid.toFormat("0.00000"),
                color = Color.White,
                fontSize = 12.sp,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.width(16.dp))

        // Two line text row
        Row(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = rate.code,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = rate.currency,
                    //style = typography.body2,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))

        // Favourite button
        Box(modifier = Modifier
            .size(48.dp)
            .align(Alignment.CenterVertically)
            .clickable(enabled = onIconClick != null) {
                favouriteRemembered = !favouriteRemembered
                onIconClick?.invoke(rate, favouriteRemembered)
            }
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center),
                imageVector = if (favouriteRemembered) {
                    Icons.Default.Favorite
                } else {
                    Icons.Default.FavoriteBorder
                },
                contentDescription = null,
                tint = Color.Red
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
fun LazyListItemPreview() {
    ExchangeRatesItem(
        index = 0,
        rate = Rate("dolar australijski", "AUD", 2f),
        size = 20,
        onItemClick = null,
        onIconClick = null,
        favourite = true
    )
}