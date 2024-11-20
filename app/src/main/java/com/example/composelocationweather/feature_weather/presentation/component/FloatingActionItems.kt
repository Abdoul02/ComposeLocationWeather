package com.example.composelocationweather.feature_weather.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composelocationweather.R

@Composable
fun FloatingActionItems(
    modifier: Modifier = Modifier,
    onGotoMap: () -> Unit,
    onGotoLocations: () -> Unit,
    onSaveLocation: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        ActionItem(icon = R.drawable.ic_map, title = "Go to Map", onClick = onGotoMap)
        ActionItem(
            icon = R.drawable.ic_current_location,
            title = "Go to Locations",
            onClick = onGotoLocations
        )
        ActionItem(
            icon = R.drawable.ic_favorite,
            title = "Add to favorite",
            onClick = onSaveLocation
        )
    }
}

@Composable
fun ActionItem(
    modifier: Modifier = Modifier,
    icon: Int,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(icon),
            tint = Color.White,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = title, color = Color.White)
    }
}

@Preview
@Composable
fun ActionItemPreview() {
    ActionItem(icon = R.drawable.ic_favorite, title = "Go to favorites") {}
}

@Preview
@Composable
fun FloatingActionItemsPreview() {
    FloatingActionItems(
        onGotoLocations = {},
        onSaveLocation = {},
        onGotoMap = {}
    )
}