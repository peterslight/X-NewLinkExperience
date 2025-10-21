package com.peterstev.x_newlinkexperience.twitter

import android.webkit.WebView
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.launch

@Composable
fun PostInteractiveScreen(
    onDismiss: () -> Unit = {}
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var screenHeight by remember { mutableIntStateOf(0) }
    val surfaceOffset = remember { Animatable(0f) }
    var isDismissing by remember { mutableStateOf(false) }

    var maxOffset by remember { mutableFloatStateOf(0f) }
    var midOffset by remember { mutableFloatStateOf(0f) }

    BackHandler {
        scope.launch {
            surfaceOffset.animateTo(0f, tween(300))
            isDismissing = true
            onDismiss()
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .onSizeChanged { size ->
                screenHeight = size.height
                maxOffset = screenHeight * 0.83f
                midOffset = screenHeight * 0.7f
            }
            .statusBarsPadding()
    ) {
        AndroidView(
            factory = {
                object : WebView(context) {
                    override fun onScrollChanged(
                        l: Int, t: Int, oldl: Int, oldt: Int
                    ) {
                        super.onScrollChanged(l, t, oldl, oldt)
                        val delta = (t - oldt).toFloat()
                        scope.launch {
                            surfaceOffset.animateTo(
                                (surfaceOffset.value + delta * 1.5f).coerceIn(
                                    midOffset,
                                    maxOffset
                                )
                            )
                        }
                    }
                }.apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    loadUrl("https://developer.android.com")
                }
            },
            modifier = Modifier
                .fillMaxSize()
        )

        if (screenHeight > 0) {
            Column {
                if (!isDismissing) {
                    WebViewAddressBar(
                        maxOffset = maxOffset,
                        screenHeight = screenHeight,
                        surfaceOffset = surfaceOffset
                    ) {
                        isDismissing = true
                        onDismiss()
                    }
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .offset { IntOffset(0, surfaceOffset.value.toInt()) },
                    color = MaterialTheme.colorScheme.surface
                ) {
                    PostScreen(isInteractiveMode = true)
                }
            }
        }

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = !isDismissing,
            exit = slideOutVertically(targetOffsetY = { it / 2 }) + fadeOut(tween(200))
        ) {
            PostFooter(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .background(
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .height(64.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }

    LaunchedEffect(screenHeight) {
        if (screenHeight > 0)
            surfaceOffset.animateTo(midOffset, tween(400))
    }
}