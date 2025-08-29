package com.examle.littlelemonapp


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
@Composable
fun HomeScreen(navController: NavHostController,viewModel: MenuViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Or your app's background color
    ) {
        val menuItems = viewModel.menuItems.collectAsState().value
        var selectedCategory by remember { mutableStateOf("All") }
        var searchPhrase by remember { mutableStateOf("") }

        // 3. Perform the filtering based on BOTH category and search phrase
        val filteredMenuItems = remember(menuItems, selectedCategory, searchPhrase) {
            if(selectedCategory == "All")
                menuItems
            else
            menuItems
                .filter {
                    it.category.equals(selectedCategory, ignoreCase = true)
                }
                .filter {
                    it.title.contains(searchPhrase, ignoreCase = true)
                }
        }


        val categories = menuItems.map { it.category }.distinct()

        LittleLemonHeader(navController)
        HeroSection()
        SearchBar(onSearch = { newPhrase ->
            searchPhrase  = newPhrase

        })
        MenuCategoryFilters( categories = categories, // The list of all unique categories
            selectedCategory = selectedCategory, // The currently selected category state
            onCategorySelected = { newCategory -> // The function to run when a button is clicked
                selectedCategory = newCategory})
        MenuItems(menuItems =filteredMenuItems)

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(modifier = Modifier.height(100.dp))
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.height(100.dp)
            )
        }
    }
}
val PrimaryGreen = Color(0xFF495E57)
val PrimaryYellow = Color(0xFFF4CE14)
val SecondaryWhite = Color(0xFFEDEFEE)
val SecondaryLightGray = Color(0xFFDCDCDC)
val Karry = Color(0xFFEE9972)

@Composable
fun LittleLemonHeader(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp) // Standard AppBar height
            .padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Spacer to push logo to center if profile image is on the right
        Spacer(modifier = Modifier.width(48.dp)) // Adjust based on profile image size

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier
                .size(200.dp),
            contentScale = ContentScale.Fit
        )

        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .clickable{
                    navController.navigate(Destinations.Profile.route)

                }
        )
    }
}

@Composable
fun HeroSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(PrimaryGreen)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = "Little Lemon",
            color = PrimaryYellow,
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            text = "Chicago",
            color = SecondaryWhite,
            fontSize = 25.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "We are a family owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                color = SecondaryWhite,
                fontSize = 16.sp,
                modifier = Modifier
                    .weight(0.6f)
                    .padding(end = 8.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.heroimage004),
                contentDescription = "Hero Image",
                modifier = Modifier
                    .weight(0.4f)
                    .height(130.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }

    }
}

@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var searchText by remember { mutableStateOf("") }

    OutlinedTextField(
        value = searchText,
        onValueChange = {
            searchText = it
            onSearch(it) // Call the search callback
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        label = { Text("Enter search phrase", color = PrimaryGreen) }, // Adjusted color for theme
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(color = Color.Black), // Adjusted color for theme
        shape = RoundedCornerShape(8.dp),

    )
}
@Composable
fun MenuItems(menuItems: List<MenuItemEntity>) {
    // You can change the log to be more informative about recomposition
    Log.d("MenuItems", "Recomposing with ${menuItems.size} items")

    // If the list is empty, it means we are in the initial loading state.
    if (menuItems.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Show a loading spinner while waiting for the data
            CircularProgressIndicator()
        }
    } else {
        // Once the list has data, display it in an efficient LazyColumn
        LazyColumn {
            items(
                items = menuItems,
                key = { it.id } // Providing a unique key is a performance best practice
            ) { item ->
                MenuItemRow(item)
            }
        }
    }
}
@OptIn(ExperimentalGlideComposeApi::class)

@Composable
fun MenuItemRow(item: MenuItemEntity) {
    Card( modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Text(
                        text = item.price, // Format price to 2 decimal places
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                AsyncImage(
                    model = item.image,
                    contentDescription = "${item.title} image",
                    modifier = Modifier
                        .size(90.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
            }
            Divider(modifier = Modifier.padding(start = 8.dp, end = 8.dp))
        }
    }
}

@Composable
fun MenuCategoryFilters(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
//    onCategorySelected: (String) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)) {
        // The "ORDER FOR DELIVERY!" title
        Text(
            text = "ORDER FOR DELIVERY!",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // This Row is the key part for horizontal sliding
        Row(
            modifier = Modifier
                .fillMaxWidth()
                // The .horizontalScroll modifier makes the content scrollable left and right
                .horizontalScroll(rememberScrollState()),
            // This adds consistent spacing between each button
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Loop through the list of categories to create a button for each
            categories.forEach { category ->
                Button(
                    onClick = {
                        // When a button is clicked, call the lambda to update the state
                        onCategorySelected(category)
                    },
//                    colors = ButtonDefaults.buttonColors(
//                        // Change color based on whether this category is the selected one
//                        backgroundColor = if (category == selectedCategory) PrimaryGreen else HighlightGray,
//                        contentColor = if (category == selectedCategory) HighlightWhite else PrimaryGreen
//                    ),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    // Display the category name, capitalized
                    Text(text = category.replaceFirstChar { it.uppercase() })
                }
            }
        }
    }
}
val HighlightGray = Color(0xFFDCDCDC)
val HighlightWhite = Color(0xFFEDEFEE)