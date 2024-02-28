package com.biho.pixify

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.biho.ui.screen.MainRoute

class MainViewModel: ViewModel() {

    val currentRoute by mutableStateOf(MainRoute.Home.route)
}