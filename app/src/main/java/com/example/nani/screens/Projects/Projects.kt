package com.example.nani.screens.Projects

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.nani.ui.theme.NaNiTheme

@Composable
fun ProjectsScreen(navController: NavHostController)
{
    Text(
        text = "To be Done."
    )
}

@Composable
fun ProjectsGroup() {


}
@Preview
@Composable
fun ProjectsPreview(){
    NaNiTheme {
        ProjectsGroup()
    }
}