package com.example.nani.screens.Analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.nani.R

@Composable
fun AnalyticsScreen(navController: NavHostController)
{    Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .padding(top = 50.dp, start = 10.dp, end = 10.dp)
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
) {
    Text(
        text = stringResource(R.string.txtWelcome),
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.primary,
    )
}
}

@Composable
fun AnalyticsGroup() {


}
@Preview
@Composable
fun AnalyticsPreview(){
    AnalyticsScreen(
        navController = rememberNavController()
    )
}