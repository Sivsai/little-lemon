package com.examle.littlelemonapp
import android.widget.Toast
import androidx.navigation.NavHostController

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.examle.littlelemonapp.R

@Composable
fun OnboardingScreen(navController: NavHostController,context: Context) {
    // State holders for TextFields
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header logo
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(170.dp)
        )

        // Header text
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF495E57)).size(100.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Let's get to know you",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))


        Text(
            text = "Personal information",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 8.dp)
        )

        // Input fields
        Text("First name", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 4.dp).align(Alignment.Start))
        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First name") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
        )
        Text("Last name", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 4.dp).align(Alignment.Start))

        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last name") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
        )
        Text("Email", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 4.dp).align(Alignment.Start))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
        )

        Spacer(modifier = Modifier.height(60.dp))

        // Yellow Register button
        Button(
            onClick = {
                if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
                    Toast.makeText(context, "Registration unsuccessful. Please enter all data.", Toast.LENGTH_SHORT).show()
                } else {
                    val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putString("firstName", firstName)
                        putString("lastName", lastName)
                        putString("email", email)
                        putBoolean("loggedIn", true)
                        apply()
                    }
                    Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                    navController.navigate(Destinations.Home.route) {
                        popUpTo(Destinations.Onboarding.route) { inclusive = true }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4CE14))
        ) {
            Text("Register", color = Color.Black)
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun OnboardingScreenPreview() {
//    MaterialTheme {
//        OnboardingScreen()
//    }
//}