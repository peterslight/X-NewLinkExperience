package com.peterstev.x_newlinkexperience

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.peterstev.x_newlinkexperience.twitter.AppScreen
import com.peterstev.x_newlinkexperience.ui.theme.XNewLinkExperienceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XNewLinkExperienceTheme {
                AppScreen()
            }
        }
    }
}