package com.example.nani.screens.projects

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.nani.R
import com.example.nani.data.Project
import com.example.nani.ui.theme.NaNiTheme
import com.example.nani.ui.theme.components.colorPicked



//segmented button, Snack bar pang clock in, Alert Dialog
@Composable
fun ProjectsScreen(navController: NavHostController, viewModel: ProjectViewModel) {
    val projectList by viewModel.projects.collectAsState()
    var selectedStatus by remember { mutableStateOf("In Progress") }

    LaunchedEffect(selectedStatus) {
        viewModel.getProjectsByStatus(selectedStatus)
    }

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        ProjectsGroup(
            selectedStatus = selectedStatus,
            onStatusChange = { status ->
                selectedStatus = status
                viewModel.getProjectsByStatus(status)
            },
            onClick = {
                viewModel.addProject("New Project", "Project Description", selectedStatus)
            },
            projects = projectList,
            onDelete = { project -> viewModel.deleteProject(project) }
        )
    }
}



@Composable
fun ProjectsGroup(
    selectedStatus: String,
    onStatusChange: (String) -> Unit,
    onClick: () -> Unit,
    projects: List<Project>,
    onDelete: (Project) -> Unit
) {
    var state by remember { mutableIntStateOf(0) }
    val titles = listOf("In Progress", "To Do", "Completed")

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
        Spacer(modifier = Modifier.height(14.dp))
        Row {
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
            Box(
                modifier = Modifier.background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.inverseOnSurface.copy(alpha = 0.2f),
                            MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                            MaterialTheme.colorScheme.secondaryContainer,
                        ),
                        startY = 5f,
                        endY = 30f
                    )
                )
            ) {
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
                    modifier = Modifier.padding(10.dp)
                ) {
                    titles.forEachIndexed { index, title ->
                        Tab(
                            selected = state == index,
                            onClick = {
                                state = index
                                onStatusChange(title)
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(50.dp))
                                .background(
                                    if (state == index)
                                        colorPicked(title)
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

        Spacer(Modifier.height(10.dp))

        // List of projects directly below the TabRow
        ListProjects(
            projects = projects,
            onDelete = onDelete
        )

        Box(modifier = Modifier.fillMaxSize()) {
            FloatingActionButton(
                onClick = { onClick() },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(vertical = 40.dp, horizontal = 30.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.edit),
                    contentDescription = "edit",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                )
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListProjects(projects: List<Project>, onDelete: (Project) -> Unit) {
    LazyColumn {
        items(projects) { project ->
            var projectexpand by remember { mutableStateOf(false) }

            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .combinedClickable(
                        onClick = { projectexpand = !projectexpand },
                        onLongClick = { onDelete(project) }  // Trigger deletion on long press
                    )
                    .padding(8.dp)  // Space between cards
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(Modifier.padding(10.dp)) {
                            Image(
                                painter = painterResource(R.drawable.jairosoft_bot),
                                contentDescription = "",
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(50.dp)
                            )
                            Column {
                                Text(
                                    text = project.name,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onSurface,
                                )
                                Text(
                                    text = project.description,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier
                                        .padding(start = 3.dp)
                                        .offset(y = -10.dp)
                                )
                            }
                        }
                    }
                    Spacer(Modifier.padding(start = 10.dp))
                    Text(
                        text = project.status,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .clip(RoundedCornerShape(50.dp))
                            .background(color = colorPicked(project.status))
                            .padding(vertical = 1.dp, horizontal = 10.dp)
                    )
                }
                if (projectexpand) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = "Details: ${project.description}")
                    }
                }
            }


        }
    }
}
