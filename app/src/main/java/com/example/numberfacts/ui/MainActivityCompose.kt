package com.example.numberfacts.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.numberfacts.App
import com.example.numberfacts.navigation.AppNavGraph
import com.example.numberfacts.ui.first_fragment.FirstFragment
import javax.inject.Inject

class MainActivityCompose : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        App.appComponent.inject(this)
        setContent {
            val navController = rememberNavController()
            AppNavGraph(navController, viewModelFactory)
                Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                    // Оборачиваем навграф внутрь Scaffold и передаём отступы (если будут)
                    Box(modifier = Modifier.padding(padding)) {
                        AppNavGraph(navController, viewModelFactory)
                    }
            }
        }
    }
}