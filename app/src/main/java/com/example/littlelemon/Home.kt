package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun Home(navController: NavController, database: AppDatabase) {
    val menuItemsList by database.menuItemDao().getAll().observeAsState(initial = emptyList())

    Scaffold(
        topBar = { Header({navController.navigate(DestProfile.route)}) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding),
        ) {
            HeroSection()
            MenuItemsSection(menuItemsList)
        }
    }
}

@Composable
private fun Header(action: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(15.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(0.5f)
                .align(Alignment.Center)
        )

        Image(
            painter = painterResource(R.drawable.profile),
            contentDescription = "profile",
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .align(Alignment.CenterEnd)
                .size(50.dp)
                .clickable { action() },
        )
    }
}

@Composable
fun HeroSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(15.dp)
    ) {
        Text(
            text = stringResource(R.string.restaurant_name),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.secondary,
        )
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(top = 0.dp)
                .offset(y = (-20).dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(end = 20.dp)
            ) {
                Text(
                    stringResource(R.string.location),
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    stringResource(R.string.short_description),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Image(
                painter = painterResource(R.drawable.hero_image),
                contentDescription = "Hero image",
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(150.dp)
                    .clip(RoundedCornerShape(15.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItemsSection(items: List<MenuItem>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        items(
            items = items,
            itemContent = { menuItem ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = menuItem.title,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold,
                        )
                        Text(
                            text = menuItem.description,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .padding(bottom = 10.dp),
                            color = Color(0xFF888888)
                        )
                        Text(
                            text = "$${menuItem.price}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color(0xFF888888)
                        )
                    }

                    GlideImage(
                        model = menuItem.image,
                        contentDescription = menuItem.title,
                        modifier = Modifier.size(100.dp),
                        contentScale = ContentScale.Crop,
                        requestBuilderTransform = {
                            it.placeholder(R.drawable.hero_image).error(R.drawable.hero_image)
                        }
                    )
                }
            }
        )
    }

}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val navController = rememberNavController()
    LittleLemonTheme(
        dynamicColor = false
    ) {
        Scaffold(
            topBar = { Header({navController.navigate(DestProfile.route)}) }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(innerPadding),
            ) {
                HeroSection()
                MenuItemsSection(listOf(
                    MenuItem(1, "Greek Salad", "The famous greek salad of crispy lettuce, peppers, olives, our Chicago.",
                        "12.99", "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/greekSalad.jpg?raw=true", "Starters"),
                    MenuItem(2, "Lemon Desert", "Traditional homemade Italian Lemon Ricotta Cake.",
                        "7.99", "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/lemonDessert%202.jpg?raw=true", "desserts"),
                    MenuItem(3, "Grilled Fish", "Our Bruschetta is made from grilled bread that has been smeared with garlic and seasoned with salt and olive oil.",
                        "20.00", "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/grilledFish.jpg?raw=true", "mains"),
                    MenuItem(4, "Pasta", "Penne with fried aubergines, cherry tomatoes, tomato sauce, fresh chili, garlic, basil & salted ricotta cheese.",
                        "18.99", "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/pasta.jpg?raw=true", "mains"),
                ))
            }
        }
    }
}