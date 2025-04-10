//package com.example.nani.screens.projects
//
//
//
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.combinedClickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.AlertDialog
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.Divider
//import androidx.compose.material3.FloatingActionButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.RadioButton
//import androidx.compose.material3.RadioButtonDefaults
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Tab
//import androidx.compose.material3.TabRow
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.ColorFilter
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.zIndex
//import androidx.navigation.NavHostController
//import com.example.nani.R
//import com.example.nani.data.Project
//import com.example.nani.ui.theme.components.colorPicked
//
//
//
////segmented button, Snack bar pang clock in, Alert Dialog
//@Composable
//fun ProjectsScreen(navController: NavHostController, viewModel: ProjectViewModel) {
//    val projectList by viewModel.projects.collectAsState()
//    var selectedStatus by remember { mutableStateOf("In Progress") }
//
//    LaunchedEffect(selectedStatus) {
//        viewModel.getProjectsByStatus(selectedStatus)
//    }
//
//    Surface(
//        color = MaterialTheme.colorScheme.background,
//        modifier = Modifier.fillMaxSize()
//    ) {
//        ProjectsGroup(
//            selectedStatus = selectedStatus,
//            onStatusChange = { status ->
//                selectedStatus = status
//                viewModel.getProjectsByStatus(status)
//            },
//            onClick = { projectName, projectDescription, projectMoreDescription, projectStatus ->
//                viewModel.addProject(projectName, projectDescription, projectMoreDescription, projectStatus)
//            },
//            projects = projectList,
//            onDelete = { project -> viewModel.deleteProject(project) },
//            onEdit = { project -> viewModel.updateProject(project) }
//        )
//
//    }
//}
//
//
//@Composable
//fun ProjectsGroup(
//    selectedStatus: String,
//    onStatusChange: (String) -> Unit,
//    onClick: (String, String, String, String) -> Unit,
//    projects: List<Project>,
//    onDelete: (Project) -> Unit,
//    onEdit: (Project) -> Unit // Add this parameter
//) {
//
//    var state by remember { mutableIntStateOf(0) }
//    val titles = listOf("In Progress", "To Do", "Completed")
//    var projectMoreDescription by remember { mutableStateOf("") }
//
//    // Dialog state
//    var showDialog by remember { mutableStateOf(false) }
//    var projectName by remember { mutableStateOf("") }
//    var projectDescription by remember { mutableStateOf("") }
//    var projectStatus by remember { mutableStateOf(titles[0]) }
//
//    Column(
//        modifier = Modifier
//            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.background)
//    ) {
//        Box(
//            modifier = Modifier.padding(bottom = 5.dp)
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.jairosoft),
//                contentDescription = "Logo",
//                modifier = Modifier.size(40.dp)
//            )
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Center,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(
//                    text = "Jairosoft",
//                    fontSize = 24.sp,
//                    color = MaterialTheme.colorScheme.onSurface,
//                    style = MaterialTheme.typography.titleLarge,
//                )
//            }
//        }
//        Spacer(modifier = Modifier.height(14.dp))
//        Row {
//            Spacer(modifier = Modifier.width(5.dp))
//            Text(
//                text = "Projects",
//                fontSize = 30.sp,
//                color = MaterialTheme.colorScheme.onSurface,
//                style = MaterialTheme.typography.titleLarge
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//        }
//
//        Card(
//            modifier = Modifier
//                .clip(RoundedCornerShape(60.dp))
//        ) {
//            Box(
//                modifier = Modifier.background(
//                    brush = Brush.verticalGradient(
//                        colors = listOf(
//                            MaterialTheme.colorScheme.inverseOnSurface.copy(alpha = 0.2f),
//                            MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
//                            MaterialTheme.colorScheme.secondaryContainer,
//                        ),
//                        startY = 5f,
//                        endY = 30f
//                    )
//                )
//            ) {
//                TabRow(
//                    selectedTabIndex = state,
//                    indicator = {},
//                    divider = {
//                        Divider(
//                            color = Color.Transparent,
//                            thickness = 0.dp
//                        )
//                    },
//                    containerColor = Color.Transparent,
//                    modifier = Modifier.padding(10.dp)
//                ) {
//                    titles.forEachIndexed { index, title ->
//                        Tab(
//                            selected = state == index,
//                            onClick = {
//                                state = index
//                                onStatusChange(title)
//                            },
//                            modifier = Modifier
//                                .clip(RoundedCornerShape(50.dp))
//                                .background(
//                                    if (state == index)
//                                        colorPicked(title)
//                                    else
//                                        Color.Transparent
//                                )
//                                .padding(horizontal = 8.dp, vertical = 4.dp)
//                        ) {
//                            Text(
//                                text = title,
//                                style = MaterialTheme.typography.labelSmall,
//                                color = if (state == index)
//                                    MaterialTheme.colorScheme.onSurface
//                                else
//                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
//                            )
//                        }
//                    }
//                }
//            }
//        }
//
//        Spacer(Modifier.height(10.dp))
//
//        Box(modifier = Modifier.fillMaxSize()) {
//            ListProjects(
//                projects = projects,
//                onDelete = onDelete,
//                onEdit = { updatedProject ->
//                    onEdit(updatedProject)
//                },
//                modifier = Modifier.padding(bottom = 80.dp)
//            )
//
//
//            FloatingActionButton(
//                onClick = { showDialog = true },
//                modifier = Modifier
//                    .align(Alignment.BottomEnd)
//                    .padding(20.dp)
//                    .zIndex(1f)
//            ) {
//                Image(
//                    painter = painterResource(R.drawable.edit),
//                    contentDescription = "edit",
//                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
//                )
//            }
//        }
//    }
//
//    // AlertDialog for Adding Project
//    if (showDialog) {
//        AlertDialog(
//            onDismissRequest = { showDialog = false },
//            title = {
//                Text(
//                    "Add New Project",
//                    color = MaterialTheme.colorScheme.onSurface,
//                    style = MaterialTheme.typography.titleLarge
//                )
//            },
//            text = {
//                Column {
//                    OutlinedTextField(
//                        value = projectName,
//                        onValueChange = { projectName = it },
//                        label = {
//                            Text(
//                                "Project Name",
//                                style = MaterialTheme.typography.labelSmall,
//                                color = MaterialTheme.colorScheme.onSurface
//                            )
//                        }
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//                    OutlinedTextField(
//                        value = projectDescription,
//                        onValueChange = { projectDescription = it },
//                        label = {
//                            Text(
//                                "Project Description",
//                                style = MaterialTheme.typography.labelSmall,
//                                color = MaterialTheme.colorScheme.onSurface
//                            )
//                        }
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//                    OutlinedTextField(
//                        value = projectMoreDescription,
//                        onValueChange = { projectMoreDescription = it },
//                        label = {
//                            Text(
//                                "More Description",
//                                style = MaterialTheme.typography.labelSmall,
//                                color = MaterialTheme.colorScheme.onSurface
//                            )
//                        }
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    Text(
//                        "Status:",
//                        style = MaterialTheme.typography.labelSmall,
//                        color = MaterialTheme.colorScheme.onSurface
//                    )
//                    titles.forEach { status ->
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .clickable { projectStatus = status }
//                        ) {
//                            RadioButton(
//                                selected = projectStatus == status,
//                                onClick = { projectStatus = status }
//                            )
//                            Text(
//                                text = status,
//                                style = MaterialTheme.typography.labelSmall,
//                                color = MaterialTheme.colorScheme.onSurface
//                            )
//                        }
//                    }
//                }
//            },
//            confirmButton = {
//                TextButton(
//                    onClick = {
//                        if (projectName.isNotBlank() && projectDescription.isNotBlank()) {
//                            onClick(
//                                projectName,
//                                projectDescription,
//                                projectMoreDescription,
//                                projectStatus
//                            )
//                            showDialog = false
//                            projectName = ""
//                            projectDescription = ""
//                            projectMoreDescription = ""
//                            projectStatus = titles[0]
//                        }
//                    }
//                ) {
//                    Text("Add")
//                }
//            },
//            dismissButton = {
//                TextButton(onClick = { showDialog = false }) {
//                    Text("Cancel")
//                }
//            }
//        )
//    }
//
//}
//
//
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun ListProjects(
//    projects: List<Project>,
//    onDelete: (Project) -> Unit,
//    onEdit: (Project) -> Unit,
//    modifier: Modifier
//) {
//    var showEditDialog by remember { mutableStateOf(false) }
//    var editableProject by remember { mutableStateOf<Project?>(null) }
//
//    LazyColumn {
//        items(projects) { project ->
//            var projectexpand by remember { mutableStateOf(false) }
//
//            Card(
//                shape = RoundedCornerShape(12.dp),
//                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .border(
//                        1.dp,
//                        MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.5f),
//                        shape = RoundedCornerShape(12.dp)
//                    )
//                    .combinedClickable(
//                        onClick = { projectexpand = !projectexpand },
//                        onLongClick = {
//                            editableProject = project
//                            showEditDialog = true
//                        }
//                    )
//                    .padding(8.dp)
//            ) {
//                Box(Modifier.fillMaxWidth()) {
//                    Row(
//                        modifier = Modifier
//                            .align(Alignment.CenterStart)
//                            .padding(10.dp),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Image(
//                            painter = painterResource(R.drawable.jairosoft_bot),
//                            contentDescription = "",
//                            modifier = Modifier
//                                .clip(CircleShape)
//                                .size(50.dp)
//                        )
//                        Spacer(modifier = Modifier.width(8.dp))
//                        Column(
//                            modifier = Modifier.weight(1f) // Take up remaining space
//                        ) {
//                            Text(
//                                text = project.name,
//                                style = MaterialTheme.typography.titleLarge,
//                                color = MaterialTheme.colorScheme.onSurface,
//                            )
//                            Text(
//                                text = project.description,
//                                style = MaterialTheme.typography.labelMedium,
//                                color = MaterialTheme.colorScheme.onSurface,
//                                modifier = Modifier.padding(start = 3.dp)
//                            )
//                        }
//                    }
//
//                    // Status indicator on the right
//                    Box(
//                        modifier = Modifier
//                            .align(Alignment.CenterEnd)
//                            .padding(end = 10.dp)
//                            .size(16.dp)
//                            .clip(CircleShape)
//                            .background(colorPicked(project.status))
//                    )
//                }
//
//                if (projectexpand) {
//                    Column(
//                        verticalArrangement = Arrangement.spacedBy(4.dp),
//                        modifier = Modifier.padding(8.dp)
//                    ) {
//                        Text(
//                            text = "Details: ${project.moreDescription}",
//                            color = MaterialTheme.colorScheme.onSurface
//                        )
//                    }
//                }
//            }
//            Spacer(modifier = Modifier.padding(10.dp))
//        }
//    }
//
//    // Edit Dialog
//    if (showEditDialog && editableProject != null) {
//        var editedName by remember { mutableStateOf(editableProject!!.name) }
//        var editedDescription by remember { mutableStateOf(editableProject!!.description) }
//        var editedMoreDescription by remember { mutableStateOf(editableProject!!.moreDescription) }
//        var editedStatus by remember { mutableStateOf(editableProject!!.status) }
//
//        AlertDialog(
//            onDismissRequest = { showEditDialog = false },
//            title = {
//                Text("Edit Project", color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.titleLarge,)
//            },
//            text = {
//                Column {
//                    OutlinedTextField(
//                        value = editedName,
//                        onValueChange = { editedName = it },
//                        label = { Text("Project Name",   style = MaterialTheme.typography.labelSmall,
//                            color = MaterialTheme.colorScheme.onSurface) }
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//                    OutlinedTextField(
//                        value = editedDescription,
//                        onValueChange = { editedDescription = it },
//                        label = { Text("Project Description",  style = MaterialTheme.typography.labelSmall,
//                            color = MaterialTheme.colorScheme.onSurface) }
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//                    OutlinedTextField(
//                        value = editedMoreDescription,
//                        onValueChange = { editedMoreDescription = it },
//                        label = { Text("More Description",   style = MaterialTheme.typography.labelSmall,
//                            color = MaterialTheme.colorScheme.onSurface) }
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    Text("Status:",   style = MaterialTheme.typography.labelSmall,
//                        color = MaterialTheme.colorScheme.onSurface)
//                    val statuses = listOf("In Progress", "To Do", "Completed", )
//                    statuses.forEach { status ->
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            modifier = Modifier.clickable { editedStatus = status }
//                        ) {
//                            RadioButton(
//                                selected = (editedStatus == status),
//                                onClick = { editedStatus = status }
//                            )
//                            Text(text = status,   style = MaterialTheme.typography.labelSmall,
//                                color = MaterialTheme.colorScheme.onSurface)
//                        }
//                    }
//                }
//            },
//            confirmButton = {
//                TextButton(onClick = {
//                    if (editedName.isNotBlank() && editedDescription.isNotBlank()) {
//                        val updatedProject = editableProject!!.copy(
//                            name = editedName,
//                            description = editedDescription,
//                            moreDescription = editedMoreDescription,
//                            status = editedStatus
//                        )
//                        onEdit(updatedProject)
//                        showEditDialog = false
//                    }
//                }) {
//                    Text("Save")
//                }
//            },
//            dismissButton = {
//                TextButton(onClick = { showEditDialog = false }) {
//                    Text("Cancel")
//                }
//            }
//        )
//    }
//}
