package com.example.littlelemon

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.ui.theme.PRIM_Yellow
import kotlin.math.log

@Composable
fun Profile(navController: NavController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(Navigation.USER_SHARED, Context.MODE_PRIVATE)
    val firstName = sharedPreferences.getString(Navigation.USER_FIRSTNAME, "").toString()
    val lastName = sharedPreferences.getString(Navigation.USER_LASTNAME, "").toString()
    val email = sharedPreferences.getString(Navigation.USER_EMAIL, "").toString()

    fun logOut() {
        sharedPreferences.edit().clear().apply()
        navController.navigate(DestOnboarding.route)
    }

    Scaffold(
        topBar = {Header()},
        bottomBar = {LogoutFooter({ logOut() })}
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp, start = 20.dp, end = 20.dp, top = 100.dp),
                text = "Personal information",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            ) {
                CustomInfoField(
                    text = firstName,
                    label = "First name",
                    modifier = Modifier.padding(bottom = 25.dp),
                )
                CustomInfoField(
                    text = lastName,
                    label = "Last name",
                    modifier = Modifier.padding(bottom = 25.dp),
                    )
                CustomInfoField(
                    text = email,
                    label = "Email",
                )
            }
        }
    }
}

@Composable
private fun Header() {
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
    }
}

@Composable
private fun LogoutFooter(logout: () -> Unit) {
    Button(
        onClick = logout,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PRIM_Yellow),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary),
    ) {
        Text(
            text = "Log out",
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun CustomInfoField(text: String, label: String = "", modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 14.sp
        )
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color(0xFFCCCCCC),
                    shape = RoundedCornerShape(5.dp)
                )
                .padding(8.dp),
            color = MaterialTheme.colorScheme.onSecondary,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    val navController = rememberNavController()
    LittleLemonTheme(
        dynamicColor = false
    ) {
        Profile(navController)
    }
}