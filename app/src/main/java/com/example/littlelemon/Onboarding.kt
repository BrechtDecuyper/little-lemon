package com.example.littlelemon

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun Onboarding(navController: NavController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(Navigation.USER_SHARED, Context.MODE_PRIVATE)

    fun submitForm() {
        if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
            Toast.makeText(
                context,
                "Registration unsuccessful. Please enter all data.",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            sharedPreferences.edit()
                .putString(Navigation.USER_FIRSTNAME, firstName)
                .putString(Navigation.USER_LASTNAME, lastName)
                .putString(Navigation.USER_EMAIL, email)
                .apply()

            Toast.makeText(
                context,
                "Registration successful!",
                Toast.LENGTH_SHORT
            ).show()

            navController.navigate(DestHome.route)
        }
    }

    Scaffold(
        topBar = { Header() },
        bottomBar = { SubmitFooter(::submitForm) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(vertical = 40.dp),
                text = "Let's get to know you",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, bottom = 30.dp)
            ) {
                Text(
                    text = "Personal information",
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(vertical = 40.dp)
                )
                OutlinedTextField(
                    value = firstName,
                    onValueChange = {firstName = it},
                    label = {Text("First name")},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                )
                OutlinedTextField(
                    value = lastName,
                    onValueChange = {lastName = it},
                    label = {Text("Last name")},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = {email = it},
                    label = {Text("Email")},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                )
            }
        }
    }
}

@Composable
private fun Header() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .background(MaterialTheme.colorScheme.background)
            .height(100.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .aspectRatio(1f),
        )
    }
}

@Composable
private fun SubmitFooter(submitForm: () -> Unit) {
    Button(
        onClick = submitForm,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary),
    ) {
        Text(
            text = "Register",
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    val navController = rememberNavController()
    LittleLemonTheme {
        Onboarding(navController)
    }
}