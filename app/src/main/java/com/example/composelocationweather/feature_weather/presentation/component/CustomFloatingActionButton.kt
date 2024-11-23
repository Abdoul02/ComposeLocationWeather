package com.example.composelocationweather.feature_weather.presentation.component

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composelocationweather.ui.theme.Indingo
import com.example.composelocationweather.util.TestTags

@Composable
fun CustomFloatingActionButton(
    expandable: Boolean,
    fabIcon: ImageVector = Icons.Default.Add,
    onFabClick: () -> Unit,
    onGotoMap: () -> Unit,
    onGotoLocations: () -> Unit,
    onSaveLocation: () -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }
    if (!expandable) { // Close the expanded fab if you change to non expandable nav destination
        isExpanded = false
    }

    val fabSize = 64.dp
    val expandedFabWidth by animateDpAsState(
        targetValue = if (isExpanded) 200.dp else fabSize,
        animationSpec = spring(dampingRatio = 3f),
        label = ""
    )
    val expandedFabHeight by animateDpAsState(
        targetValue = if (isExpanded) 58.dp else fabSize,
        animationSpec = spring(dampingRatio = 3f),
        label = ""
    )

    Column {
        Box(
            modifier = Modifier
                .offset(y = (25).dp)
                .size(
                    width = expandedFabWidth,
                    height = (animateDpAsState(
                        if (isExpanded) 200.dp else 0.dp,
                        animationSpec = spring(dampingRatio = 4f),
                        label = ""
                    )).value
                )
                .background(
                    Indingo,
                    shape = RoundedCornerShape(18.dp)
                )
                .padding(16.dp)
        ) {
            FloatingActionItems(
                onSaveLocation = { onSaveLocation() },
                onGotoMap = { onGotoMap() },
                onGotoLocations = { onGotoLocations() },
            )
        }

        FloatingActionButton(
            onClick = {
                onFabClick()
                if (expandable) {
                    isExpanded = !isExpanded
                }
            },
            modifier = Modifier
                .width(expandedFabWidth)
                .height(expandedFabHeight)
                .testTag(TestTags.LOCATION_NAV_FAB),
            shape = RoundedCornerShape(18.dp),
            containerColor = Indingo

        ) {

            Icon(
                imageVector = fabIcon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .offset(
                        x = animateDpAsState(
                            if (isExpanded) (-70).dp else 0.dp,
                            animationSpec = spring(dampingRatio = 3f),
                            label = ""
                        ).value
                    )
            )

            Text(
                text = "Click to close",
                softWrap = false,
                color = Color.White,
                modifier = Modifier
                    .offset(
                        x = animateDpAsState(
                            if (isExpanded) 10.dp else 50.dp,
                            animationSpec = spring(dampingRatio = 3f),
                            label = ""
                        ).value
                    )
                    .alpha(
                        animateFloatAsState(
                            targetValue = if (isExpanded) 1f else 0f,
                            animationSpec = tween(
                                durationMillis = if (isExpanded) 350 else 100,
                                delayMillis = if (isExpanded) 100 else 0,
                                easing = EaseIn
                            ),
                            label = ""
                        ).value
                    )
            )

        }
    }
}

@Preview
@Composable
fun CustomFloatingActionButtonPreview() {
    val fabIcon = Icons.Default.Add
    CustomFloatingActionButton(
        true,
        fabIcon = fabIcon,
        onFabClick = {},
        onSaveLocation = {},
        onGotoMap = {},
        onGotoLocations = {}
    )
}