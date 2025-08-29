package com.examle.littlelemonapp


import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@SuppressLint("UseKtx")
@Composable
fun ProfileScreen(navController: NavController, context: Context) {
    val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    val firstName = sharedPref.getString("firstName", "") ?: ""
    val lastName = sharedPref.getString("lastName", "") ?: ""
    val email = sharedPref.getString("email", "") ?: ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header with Logo
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 16.dp)
        )

        // Title
        Text(
            text = "Personal information",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 12.dp)
        )

        // First Name
        Text(text = "First name", fontWeight = FontWeight.SemiBold)
        Text(
            text = firstName,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .border(1.dp, Color.LightGray, RoundedCornerShape(6.dp))
                .padding(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Last Name
        Text(text = "Last name", fontWeight = FontWeight.SemiBold)
        Text(
            text = lastName,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .border(1.dp, Color.LightGray, RoundedCornerShape(6.dp))
                .padding(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Email
        Text(text = "Email", fontWeight = FontWeight.SemiBold)
        Text(
            text = email,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .border(1.dp, Color.LightGray, RoundedCornerShape(6.dp))
                .padding(12.dp)
        )

        Spacer(modifier = Modifier.height(100.dp))

        // Logout Button
        Button(
            onClick = {
                sharedPref.edit().clear().apply()
                navController.navigate("onboarding") {
                    popUpTo("profile") { inclusive = true }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107)), // Yellow
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Log out", color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    // Preview without navigation
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.height(80.dp)
        )
        Text("Profile information:")
        Text("First name: John")
        Text("Last name: Doe")
        Text("Email: john@example.com")
    }
}
