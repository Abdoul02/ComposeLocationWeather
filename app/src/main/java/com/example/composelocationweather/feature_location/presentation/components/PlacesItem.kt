package com.example.composelocationweather.feature_location.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.composelocationweather.R
import com.example.composelocationweather.feature_location.domain.model.places.Result

@Composable
fun PlacesItem(
    modifier: Modifier = Modifier,
    result: Result
) {

    Card(
        shape = RoundedCornerShape(9.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary),
        modifier = modifier
            .padding(5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = result.icon,
                contentDescription = result.name,
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                modifier = Modifier
                    .size(50.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = result.name)
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RatingStar(rating = result.rating.toFloat(), spaceBetween = 2.dp)
                Spacer(modifier = Modifier.width(3.dp))
                Text(text = "(${result.rating})")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
            ) {
                val isOpen = when (result.opening_hours?.open_now) {
                    true -> "Yes"
                    false -> "No"
                    else -> "No information provided"
                }
                Text(text = "Open: $isOpen")
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "Near: ${result.vicinity}")
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    result.types.take(3).map { it.replace("_", "-") }.forEach { type ->
                        SuggestionChip(
                            onClick = {},
                            label = { Text(text = type) }
                        )
                    }
                }
            }
        }
    }
}