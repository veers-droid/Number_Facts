package com.example.numberfacts.ui.second_fragment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.example.numberfacts.dagger.daggerViewModel
import com.example.numberfacts.ui.FactsViewModel


/**
 * Second screen where you can read the fact about written \ chosen or random number
 */
@Composable
fun SecondFragment(navHostController: NavHostController, viewModelProvider: ViewModelProvider.Factory, number : String? = null, passedFact : String?) {

    val viewModel = daggerViewModel<FactsViewModel>(viewModelProvider)

    val numberFact by viewModel.fact.observeAsState(initial = "")

    val displayFact = remember(passedFact, numberFact) {
        passedFact ?: numberFact
    }

    //On screen launch if don't have the fact already requests for new fact
    LaunchedEffect(passedFact, number) {
        if (passedFact == null) {
            number?.takeIf { it.isNotBlank() }?.let {
                viewModel.getFactAboutNumber(it)
            } ?: viewModel.getRandomFact()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp, start = 15.dp, end = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Chosen number:${number.takeUnless { it.isNullOrBlank() } ?: "Random"}",
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = displayFact,
            fontSize = 22.sp,
            maxLines = 20,
            modifier = Modifier.fillMaxWidth()
        )
    }
}