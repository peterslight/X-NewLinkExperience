package com.peterstev.x_newlinkexperience.twitter


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.peterstev.x_newlinkexperience.R

@Composable
fun PostFooter(
    modifier: Modifier = Modifier
) {
    var isLiked by remember { mutableStateOf(false) }
    var isBookmarked by remember { mutableStateOf(false) }

    val iconColor = MaterialTheme.colorScheme.onSurfaceVariant
    val activeLikeColor = Color.Red
    val activeBookmarkColor = Color(0xFF1D9BF0)

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f))
    ) {
        IconButton(onClick = {}) { Icon(painterResource(R.drawable.reply), null, tint = iconColor) }
        IconButton(onClick = {}) { Icon(painterResource(R.drawable.repost), null, tint = iconColor) }
        IconButton(onClick = { isLiked = !isLiked }) {
            if (isLiked) {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = null,
                    tint = activeLikeColor
                )
            } else {
                Icon(
                    Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = iconColor
                )
            }
        }
        IconButton(onClick = { isBookmarked = !isBookmarked }) {
            if (isBookmarked) {
                Icon(
                    Icons.Default.Bookmark,
                    contentDescription = null,
                    tint = activeBookmarkColor
                )
            } else {
                Icon(
                    Icons.Default.BookmarkBorder,
                    contentDescription = null,
                    tint = iconColor
                )
            }
        }
        IconButton(onClick = {}) { Icon(Icons.Default.Share, null, tint = iconColor) }
    }
}
