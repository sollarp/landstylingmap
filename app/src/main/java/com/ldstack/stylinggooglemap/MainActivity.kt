package com.ldstack.stylinggooglemap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MapsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                HomeScreen()
            }
        }
    }
}
