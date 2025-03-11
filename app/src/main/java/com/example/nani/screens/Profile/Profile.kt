package com.example.nani.screens.Profile

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.nani.R
import com.example.nani.screens.Dashboard.JairosoftAppScreen
import com.example.nani.ui.theme.NaNiTheme
import com.example.nani.ui.theme.components.arcOffset
import com.example.nani.ui.theme.components.betweenSpace
import com.example.nani.ui.theme.components.cardPadding
import com.example.nani.ui.theme.components.imageOffset
import com.example.nani.ui.theme.components.imageSize
import com.example.nani.ui.theme.components.offset
import com.example.nani.ui.theme.components.sizeCircular
import com.example.nani.ui.theme.components.textSize


//apply separation of concerns

@Composable
fun ProfileScreen(navController: NavHostController)
{
    Surface (    color = MaterialTheme.colorScheme.background,
        modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState()))
            {
        ProfileGroup(onLogout = {navController.navigate(JairosoftAppScreen.Login.name)})
    }
}

@Composable
fun ProfileGroup(onLogout: () -> Unit) {

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
                                MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    )
            ) {
                Row(modifier = Modifier.fillMaxWidth()
                    .padding(end= 10.dp, top = 20.dp), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable{
                        onLogout()
                    }
                    ){
                        Text(
                            text = "Log out",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.titleSmall
                            )
                        Image(
                            painter = painterResource(R.drawable.logout),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
                            modifier = Modifier.padding(5.dp)
                                .size(24.dp)
                        )
                    }
                }

                Image(
                    painter = painterResource(R.drawable.arc),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .align(Alignment.BottomCenter)
                        .offset(y = arcOffset())
                        .zIndex(0f)
                        ,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(1f)
                .offset(y = -(imageOffset())),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.face),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(imageSize())
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.onSurfaceVariant, CircleShape)
                    .zIndex(2f)
            )
        }}

Column {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
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
    Spacer(modifier = Modifier.height(60.dp))
Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){

        ProgressCard(
            "10",
            "Projects"
        )
        Spacer(modifier = Modifier.width((betweenSpace())))
        ProgressCard(
            "50",
            "Tasks"
        )
     }
    }
}

@Composable
fun ProgressCard(percent:String, label:String){
    OutlinedCard ( colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = .5f),
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurfaceVariant),
        )
    {
            Column (horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(cardPadding())) {
                Box {
                    CircularProgressIndicator(
                        modifier = Modifier.size(sizeCircular()).padding(20.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.background,
                    )
                }

                Text(
                    text = "$percent%",
                    color = MaterialTheme.colorScheme.primary,
                    style = textSize(),
                    modifier = Modifier.offset(y = offset(), x = 1.dp)
                )

                    Text(
                        text = label,
                        color = MaterialTheme.colorScheme.primary,
                        style = textSize(),
                        modifier = Modifier.offset(y=-20.dp)
                    )
            }
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