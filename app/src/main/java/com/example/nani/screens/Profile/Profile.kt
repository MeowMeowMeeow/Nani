package com.example.nani.screens.Profile

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.nani.R
import com.example.nani.ui.theme.NaNiTheme

@Composable
fun ProfileScreen(navController: NavHostController)
{
    Surface (    color = MaterialTheme.colorScheme.background,
        modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState()))
            {
        ProfileGroup()
    }
}

@Composable
fun ProfileGroup() {

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.background,
                                MaterialTheme.colorScheme.secondary
                            )
                        )
                    )
            ) {


                Image(
                    painter = painterResource(R.drawable.arc),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .align(Alignment.BottomCenter)
                        .offset(y = (30).dp)
                        .zIndex(0f),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(1f)
                .offset(y = (-100).dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.LightGray, CircleShape)
                    .zIndex(2f)


            )


        }}

Column(horizontalAlignment = Alignment.CenterHorizontally) {

    Spacer(modifier = Modifier.padding(130.dp))
    Text(
        text = "Name Name",
        style = MaterialTheme.typography.titleLarge, //from Type.kt refer in bubble.io specifics
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.primary, //Add sa theme later
        modifier = Modifier,

        )
    Text(
        text = "Description",
        style = MaterialTheme.typography.titleMedium, //from Type.kt refer in bubble.io specifics
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.primary, //Add sa theme later
        modifier = Modifier,

        )
}



}







@Preview(name = "Light Theme")
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProfilePreview() {
    NaNiTheme {
        ProfileScreen(
            navController = rememberNavController()
        )
    }
}