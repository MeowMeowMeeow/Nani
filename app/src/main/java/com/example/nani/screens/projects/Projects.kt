package com.example.nani.screens.projects

import android.content.res.Configuration
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.nani.R
import com.example.nani.ui.theme.NaNiTheme
import com.example.nani.ui.theme.components.colorPicked



//segmented button, Snack bar pang clock in, Alert Dialog
@Composable
fun ProjectsScreen(navController: NavHostController)
{
  ProjectsGroup(
      selected = true,
      onClick = {}
  ) 
}

@Composable
fun ProjectsGroup(    selected: Boolean,
                      onClick: () -> Unit) {
    var state by remember { mutableIntStateOf(0) }
    val titles = listOf("In Progress", "To Do", "Completed")

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Box(
                modifier = Modifier.padding(bottom = 5.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.jairosoft),
                    contentDescription = "Logo",
                    modifier = Modifier.size(40.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Jairosoft",
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }
            Spacer(
                modifier = Modifier.height(14.dp)
            )
            Row(
                modifier = Modifier
            ) {
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Projects",
                    fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(16.dp))

            }
            Card(
                modifier = Modifier
                    .clip(RoundedCornerShape(60.dp))

            ) {
    Box (modifier = Modifier
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.inverseOnSurface.copy(alpha = 0.2f),
                    MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                    MaterialTheme.colorScheme.secondaryContainer,



                ),
                        startY = 5f, // Move gradient up by 100 pixels
                endY = 30f // Customize where it ends depending on height
            )
            )){

        TabRow(
            selectedTabIndex = state,
            indicator = {},
            divider = {
                Divider(
                    color = Color.Transparent,
                    thickness = 0.dp
                )
            },
            containerColor = Color.Transparent,
            modifier = Modifier
                .padding(10.dp)


        ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = state == index,
                    onClick = { state = index },
                    modifier = Modifier
                        .clip(RoundedCornerShape(50.dp))
                        .background(
                            if (state == index)
                                colorPicked(
                                    title = title
                                )
                            else
                                Color.Transparent
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (state == index)
                            MaterialTheme.colorScheme.onSurface
                        else
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                            }
                        }
                     }
                 }

            }
            Spacer(Modifier.height(30.dp))
            //Lazy column dapat
            ListProjects()
        }

    }
}

@Composable
fun ListProjects(){
    //dapat mu take in siya sa value sa row
    //val status =

    var projectexpand by remember { mutableStateOf(false) }
Card( shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
    modifier = Modifier
        .fillMaxWidth()
        .border(
            1.dp,
            MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.5f),
            shape = RoundedCornerShape(12.dp)
        )
        .clickable {projectexpand = !projectexpand  }
) {
    Row(modifier =Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
    Column {

        Row(Modifier.padding(10.dp)) {
            Image(
                painter = painterResource(R.drawable.jairosoft_bot),
                contentDescription = "",
                modifier = Modifier.clip(CircleShape).size(50.dp)
            )
            Column {
                Text(
                    text = "Jairosoft Bot",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = "Robot Mascot",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(start = 3.dp).offset(y = -10.dp)
                )
            }
        }


    }
        Spacer(Modifier.padding(start = 10.dp))
        Text(
            text = "In Progress",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.clip(RoundedCornerShape(50.dp)).background(color = colorPicked("In Progress")).padding(vertical = 1.dp , horizontal = 10.dp)
        )
        Spacer(Modifier.width(5.dp))

        //update in utils
        Icon(
            painter = when (projectexpand){
                true ->  painterResource(id = R.drawable.drop_up)
                false -> painterResource(id = R.drawable.drop_down)
            },
            contentDescription = "Dropdown",
            modifier = Modifier.size(24.dp).padding(start = 10.dp),
            tint = MaterialTheme.colorScheme.onSurface
        )


}
    if (projectexpand) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Test")
        }
    }
}
}


@Preview(name = "Light Theme")
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProjectsPreview(){
    NaNiTheme {
        ProjectsScreen(
            navController = rememberNavController()
        )
    }
}