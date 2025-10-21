@file:OptIn(ExperimentalMaterial3Api::class)

package com.peterstev.x_newlinkexperience.twitter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.peterstev.x_newlinkexperience.R

@Composable
fun PostScreen(
    modifier: Modifier = Modifier,
    isInteractiveMode: Boolean = false,
    onImageClick: () -> Unit = {},
) {
    val mod = if (isInteractiveMode) modifier.padding(16.dp)
    else modifier
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 16.dp)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (!isInteractiveMode) {
                TopAppBar(
                    title = { Text("Post") },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.ArrowBack, contentDescription = null)
                        }
                    },
                    actions = {
                        Icon(painter = painterResource(R.drawable.grok), contentDescription = null)
                    }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = mod
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.95f))
                .padding(paddingValues = innerPadding),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(R.drawable.profile),
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Nikita Bier", fontWeight = FontWeight.Bold)
                        Icon(
                            painter = painterResource(R.drawable.user_checkmark),
                            contentDescription = null,
                            tint = Color.Blue,
                            modifier = Modifier
                                .size(16.dp)
                                .padding(start = 4.dp)
                        )

                        Image(
                            painter = painterResource(R.drawable.twitter),
                            contentDescription = null,
                            modifier = Modifier
                                .size(16.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }
                    Text("@nikitabier", color = Color.Gray, fontSize = 13.sp)
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    modifier = Modifier
                        .height(36.dp)
                        .align(Alignment.CenterVertically),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text("Subscribe")
                }
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = null,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "One of the best parts about working at X is you can share quick a mockup when inspiration strikes, rally the team and then it's in millions of people's hands a few weeks later.\n" +
                        "\n" +
                        "If you're an experienced Android engineer, we're hiring right now. \n\n" +
                        "One of the best parts about working at X is you can share quick a mockup when inspiration strikes, rally the team and then it's in millions of people's hands a few weeks later.\n" +
                        "\n" +
                        "If you're an experienced Android engineer, we're hiring right now.\n\n" +
                        "One of the best parts about working at X is you can share quick a mockup when inspiration strikes, rally the team and then it's in millions of people's hands a few weeks later.\n" +
                        "\n" +
                        "If you're an experienced Android engineer, we're hiring right now."
            )
            Spacer(modifier = Modifier.height(12.dp))
            Image(
                painter = painterResource(R.drawable.web_image),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(220.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        onImageClick.invoke()
                    }
            )

            Spacer(modifier = Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text("08:49")
                Text(" • ")
                Text("21 Aug 2025")
                Text(" • ")
                Text("65K views")
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "8 Reposts • 5 Quotes • 200 Likes • 18 Bookmarks",
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))
            PostFooter()
        }
    }
}