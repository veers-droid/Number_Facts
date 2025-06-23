package com.example.numberfacts.ui.first_fragment

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.example.numberfacts.dagger.daggerViewModel
import com.example.numberfacts.ui.FactsViewModel

/**
 * First screen where you can open fact from history or get fact about number
 */
@Composable
fun FirstFragment(navController: NavHostController, viewModelProvider: ViewModelProvider.Factory) {


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,){
        var number by remember { mutableStateOf("") }
        val viewModel = daggerViewModel<FactsViewModel>(viewModelProvider)
        viewModel.getStory()
        val story by viewModel.story

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = number,
                onValueChange = {
                    number = it
                    },
                label = {Text("Enter your number")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            RoundedButton("Get fact about your number", {
                if (number.isEmpty()) return@RoundedButton
                navController.navigate("second/$number")
            })

            RoundedButton("Get fact about random number", {
                navController.navigate("second/")
            })
        }

        /**
         * Column where we display history of got facts
         */
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) {
            items(story.size) { index ->
                val fact = story[index]
                Text(
                    text = fact,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 4.dp).clickable {
                        val numberFromFact = fact.substringBefore(" ").filter { it.isDigit() }
                        if (numberFromFact.isNotEmpty()) {
                            navController.navigate("second/$numberFromFact?fact=$fact")
                        }
                    }
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                )
            }
        }

    }
}

@Composable
fun RoundedButton(title : String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(onClick,
        modifier = modifier,
        shape = RoundedCornerShape(6.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
    ) {
        Text(title, color = Color.White)
    }
}