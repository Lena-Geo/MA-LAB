package com.example.lab6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab6.ui.theme.Lab6Theme

class RecipeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab6Theme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    RecipeContent()
                }
            }
        }
    }

    @Composable
    fun RecipeContent() {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Title for the recipe list
            Text(
                text = "Cake Recipes",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                fontSize = 26.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Recipe items
            RecipeItem(
                imageRes = R.drawable.chocolate_cake,
                title = "Chocolate Cake",
                description = "A rich and moist chocolate cake recipe. Perfect for chocolate lovers!"
            )

            RecipeItem(
                imageRes = R.drawable.cupcake,
                title = "Cupcakes",
                description = "Delicious vanilla cupcakes topped with sweet frosting."
            )

            RecipeItem(
                imageRes = R.drawable.fruit_cake,
                title = "Fruit Cake",
                description = "A classic fruit cake filled with nuts and dried fruits."
            )

            // Add more recipes here
            RecipeItem(
                imageRes = R.drawable.ice_cream_cake,
                title = "Ice Cream Cake",
                description = "A cool and refreshing ice cream cake, perfect for summer."
            )

            RecipeItem(
                imageRes = R.drawable.cheescake,
                title = "Cheesecake",
                description = "A creamy and smooth cheesecake with a graham cracker crust."
            )
        }
    }

    @Composable
    fun RecipeItem(imageRes: Int, title: String, description: String) {
        Row(modifier = Modifier.padding(bottom = 16.dp)) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 16.dp)
            )

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp)
                )
            }
        }
    }
}
