package com.peterstev.x_newlinkexperience.twitter

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.peterstev.x_newlinkexperience.ui.theme.Typography
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WebViewAddressBar(
    maxOffset: Float,
    screenHeight: Int,
    surfaceOffset: Animatable<Float, AnimationVector1D>,
    onDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var lastOffset by remember { mutableFloatStateOf(surfaceOffset.value) }
    var isDraggingDown by remember { mutableStateOf(false) }
    var iconsVisible by remember { mutableStateOf(true) }
    var isFirstLaunch by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        withFrameNanos { }
        delay(400)
        iconsVisible = true
        isFirstLaunch = false
        lastOffset = surfaceOffset.value
    }

    if (!isFirstLaunch) {
        if (surfaceOffset.value != lastOffset) {
            val draggingDown = surfaceOffset.value > lastOffset
            if (draggingDown != isDraggingDown) {
                isDraggingDown = draggingDown
                iconsVisible = !draggingDown
            }
            lastOffset = surfaceOffset.value
        }
    }

    Row(
        modifier = Modifier
            .offset { IntOffset(0, surfaceOffset.value.toInt()) }
            .background(Color.Transparent)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        val change = event.changes.firstOrNull()
                        if (change != null) {
                            val dragAmount =
                                change.position.y - change.previousPosition.y
                            if (dragAmount != 0f) {
                                val newOffset = (surfaceOffset.value + dragAmount)
                                    .coerceIn(0f, maxOffset)
                                if (newOffset < screenHeight * 0.2f) {
                                    scope.launch {
                                        surfaceOffset.animateTo(0f, tween(300))
                                        onDismiss()
                                    }
                                } else {
                                    scope.launch { surfaceOffset.snapTo(newOffset) }
                                }
                            }
                            change.consume()
                        }
                    }
                }
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AnimatedVisibility(visible = iconsVisible) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(Color.Gray, shape = CircleShape)
                    .clickable {
                        scope.launch {
                            surfaceOffset.animateTo(0f, tween(300))
                            onDismiss()
                        }
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Spacer(Modifier.weight(1.2f))
        Text(
            text = "android.com",
            modifier = Modifier
                .background(Color.Gray, shape = RoundedCornerShape(18.dp))
                .padding(vertical = 8.dp, horizontal = 24.dp)
                .wrapContentWidth(),
            style = Typography.bodyMedium.copy(
                color = Color.White,
                textAlign = TextAlign.Center
            )
        )
        Spacer(Modifier.weight(if (iconsVisible) 0.8f else 1f))
        AnimatedVisibility(visible = iconsVisible) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(Color.Gray, shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    tint = Color.White,
                    contentDescription = "Reload",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Spacer(Modifier.width(8.dp))
        AnimatedVisibility(visible = iconsVisible) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(Color.Gray, shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreHoriz,
                    contentDescription = "Overflow",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}