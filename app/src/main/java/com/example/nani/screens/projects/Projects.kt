package com.example.nani.screens.projects

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.nani.R
import com.example.nani.data.Project
import com.example.nani.ui.theme.components.colorPicked


//segmented button, Snack bar pang clock in, Alert Dialog
@Composable
fun ProjectsScreen(navController: NavHostController, viewModel: ProjectViewModel) {
    val projectList by viewModel.projects.collectAsState(initial = emptyList()) // Avoid displaying old data
    var selectedStatus by remember { mutableStateOf("In Progress") }
    var isInitialized by remember { mutableStateOf(false) } // Track initialization


    LaunchedEffect(selectedStatus) {
        if (!isInitialized) {
            viewModel.getProjectsByStatus(selectedStatus)
            isInitialized = true
        }
    }

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ProjectsGroup(
            selectedStatus = selectedStatus,
            onStatusChange = { status ->
                selectedStatus = status
                viewModel.getProjectsByStatus(status)
            },
            onClick = { projectName, projectDescription, projectMoreDescription, projectStatus ->
                viewModel.addProject(projectName, projectDescription, projectMoreDescription, projectStatus)
            },
            projects = projectList,
            onDelete = { project -> viewModel.deleteProject(project) },
            onEdit = { project -> viewModel.updateProject(project) }
        )
    }
}



@Composable
fun ProjectsGroup(
    selectedStatus: String,
    onStatusChange: (String) -> Unit,
    onClick: (String, String, String, String) -> Unit,
    projects: List<Project>,
    onDelete: (Project) -> Unit,
    onEdit: (Project) -> Unit
) {

    var state by remember { mutableIntStateOf(0) }
    val titles = listOf("In Progress", "To Do", "Completed")
    var projectMoreDescription by remember { mutableStateOf("") }

    // Dialog state
    var showDialog by remember { mutableStateOf(false) }
    var projectName by remember { mutableStateOf("") }
    var projectDescription by remember { mutableStateOf("") }
    var projectStatus by remember { mutableStateOf(titles[0]) }
    LaunchedEffect(selectedStatus) {
        state = titles.indexOf(selectedStatus)
    }

    Column(
        modifier = Modifier
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
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
                        HorizontalDivider(
                            thickness = 0.dp,
                            color = Color.Transparent
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

        Box(
            //change in conditionals
            modifier = Modifier.height(550.dp)
                .fillMaxWidth()
        ) {
            ListProjects(
                projects = projects,
                onDelete = onDelete,
                onEdit = { updatedProject ->

                    state = titles.indexOf(updatedProject.status) // Update the tab on edit
                    onStatusChange(updatedProject.status)
                    onEdit(updatedProject)
                },
            )



            FloatingActionButton(
                onClick = { showDialog = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp)
                    .zIndex(1f),
                containerColor = MaterialTheme.colorScheme.surfaceDim.copy(alpha = 0.6f),
                elevation = FloatingActionButtonDefaults.elevation(0.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.edit),
                    contentDescription = "edit",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)),
                    modifier = Modifier.size(24.dp)
                )
            }

        }
    }

    // AlertDialog for Adding Project
    if (showDialog) {
        AlertDialog(
           containerColor = MaterialTheme.colorScheme.secondaryContainer,
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    "Add New Project",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Column (
                    modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp)
                ) {
                    OutlinedTextField(
                        value = projectName,
                        onValueChange = { projectName = it },
                        label = {
                            Text(
                                "Project Name",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            focusedIndicatorColor =  MaterialTheme.colorScheme.secondary,
                            unfocusedIndicatorColor =  MaterialTheme.colorScheme.secondary,
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = projectDescription,
                        onValueChange = { projectDescription = it },
                        label = {
                            Text(
                                "Project Description",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            focusedIndicatorColor =  MaterialTheme.colorScheme.secondary,
                            unfocusedIndicatorColor =  MaterialTheme.colorScheme.secondary,
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = projectMoreDescription,
                        onValueChange = { projectMoreDescription = it },
                        label = {
                            Text(
                                "More Description",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            focusedIndicatorColor =  MaterialTheme.colorScheme.secondary,
                            unfocusedIndicatorColor =  MaterialTheme.colorScheme.secondary,
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        "Status:",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 20.sp
                    )
                    titles.forEach { status ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { projectStatus = status }
                                .fillMaxWidth()
                        ) {
                            RadioButton(
                                selected = projectStatus == status,
                                onClick = { projectStatus = status }
                            )
                            Text(
                                text = status,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (projectName.isNotBlank() && projectDescription.isNotBlank()) {
                            onClick(
                                projectName,
                                projectDescription,
                                projectMoreDescription,
                                projectStatus
                            )
                            showDialog = false
                            projectName = ""
                            projectDescription = ""
                            projectMoreDescription = ""
                            projectStatus = titles[0]
                        }
                    }
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun ListProjects(
    projects: List<Project>,
    onDelete: (Project) -> Unit,
    onEdit: (Project) -> Unit,
    modifier: Modifier = Modifier
) {
    var showEditDialog by remember { mutableStateOf(false) }
    var editableProject by remember { mutableStateOf<Project?>(null) }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp) // Spacing between cards
    ) {
        items(projects, key = { it.id }) { project ->
            var projectExpand by remember { mutableStateOf(false) }
            val dismissState = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart) {
                        onDelete(project)
                    }
                    true
                }
            )

            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart),
                background = {
                    val color = if (dismissState.targetValue == DismissValue.DismissedToStart) MaterialTheme.colorScheme.onError.copy(0.8f) else Color.Transparent
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(horizontal = 20.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.White
                        )
                    }
                },
                dismissContent = {
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
                                onClick = { projectExpand = !projectExpand },
                                onLongClick = {
                                    editableProject = project
                                    showEditDialog = true
                                }
                            )
                            .padding(8.dp)
                    ) {
                        Box(Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.jairosoft_bot),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(50.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = project.name,
                                        style = MaterialTheme.typography.titleLarge,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = project.description,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.padding(start = 3.dp)
                                    )
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(end = 10.dp)
                                    .size(16.dp)
                                    .clip(CircleShape)
                                    .background(colorPicked(project.status))
                            )
                        }

                        if (projectExpand) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Text(
                                    text = "Details: ${project.moreDescription}",
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            )

            if (showEditDialog && editableProject != null) {
                var editedName by remember { mutableStateOf(editableProject!!.name) }
                var editedDescription by remember { mutableStateOf(editableProject!!.description) }
                var editedMoreDescription by remember { mutableStateOf(editableProject!!.moreDescription) }
                var editedStatus by remember { mutableStateOf(editableProject!!.status) }

                AlertDialog(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    onDismissRequest = { showEditDialog = false },
                    title = {
                        Text(
                            "Edit Project",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.titleLarge,
                        )
                    },
                    text = {
                        Column(
                            modifier = Modifier
                                .verticalScroll(rememberScrollState())
                                .padding(8.dp)
                        ) {
                            OutlinedTextField(
                                value = editedName,
                                onValueChange = { editedName = it },
                                label = {
                                    Text("Project Name", style = MaterialTheme.typography.labelSmall,color = MaterialTheme.colorScheme.onSurface)
                                },
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    focusedIndicatorColor =  MaterialTheme.colorScheme.secondary,
                                    unfocusedIndicatorColor =  MaterialTheme.colorScheme.secondary,
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = editedDescription,
                                onValueChange = { editedDescription = it },
                                label = {
                                    Text("Project Description", style = MaterialTheme.typography.labelSmall,color = MaterialTheme.colorScheme.onSurface)
                                },
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    focusedIndicatorColor =  MaterialTheme.colorScheme.secondary,
                                    unfocusedIndicatorColor =  MaterialTheme.colorScheme.secondary,
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = editedMoreDescription,
                                onValueChange = { editedMoreDescription = it },
                                label = {
                                    Text("More Description", style = MaterialTheme.typography.labelSmall,color = MaterialTheme.colorScheme.onSurface)
                                },  modifier = Modifier
                                    .height(150.dp)
                                    .fillMaxWidth(),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    focusedIndicatorColor =  MaterialTheme.colorScheme.secondary,
                                    unfocusedIndicatorColor =  MaterialTheme.colorScheme.secondary,
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Text("Status:",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 20.sp
                            )
                            val statuses = listOf("In Progress", "To Do", "Completed")
                            statuses.forEach { status ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.clickable { editedStatus = status }
                                        .fillMaxWidth()
                                ) {
                                    RadioButton(
                                        selected = (editedStatus == status),
                                        onClick = { editedStatus = status },
                                        colors = RadioButtonDefaults.colors(
                                           unselectedColor = MaterialTheme.colorScheme.secondary,
                                            selectedColor = MaterialTheme.colorScheme.secondary,
                                            disabledUnselectedColor = MaterialTheme.colorScheme.secondary,
                                            disabledSelectedColor = MaterialTheme.colorScheme.secondary,

                                        )
                                    )
                                    Text(text = status, style = MaterialTheme.typography.labelSmall,color = MaterialTheme.colorScheme.onSurface)
                                }
                            }
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            if (editedName.isNotBlank() && editedDescription.isNotBlank()) {
                                val updatedProject = editableProject!!.copy(
                                    name = editedName,
                                    description = editedDescription,
                                    moreDescription = editedMoreDescription,
                                    status = editedStatus
                                )
                                onEdit(updatedProject)
                                showEditDialog = false
                            }
                        }) {
                            Text("Save")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showEditDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}
