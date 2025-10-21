package com.peterstev.x_newlinkexperience.twitter

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AppScreen() {
    var showInteractive by remember { mutableStateOf(false) }

    AnimatedContent(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.95f))
        ,
        targetState = showInteractive,
        transitionSpec = {
            fadeIn(tween(800)) togetherWith fadeOut(tween(800))
        },
        contentAlignment = Alignment.Center
    ) { visible ->
        if (visible) PostInteractiveScreen { showInteractive = false }
        else PostScreen(onImageClick = { showInteractive = true })
    }
}