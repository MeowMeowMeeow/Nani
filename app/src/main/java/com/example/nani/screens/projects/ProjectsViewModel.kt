package com.example.nani.screens.projects

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.nani.data.Project
import com.example.nani.network.data.ProjectDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProjectViewModel(application: Application) : AndroidViewModel(application) {

    private val projectDao = ProjectDatabase.getDatabase(application).projectDao()
    private val _projects = MutableStateFlow<List<Project>>(emptyList())
    val projects: StateFlow<List<Project>> = _projects

    var currentStatus = "In Progress"

    init {
        loadProjects(currentStatus)
    }

    private fun loadProjects(status: String) {
        viewModelScope.launch {
            _projects.value = emptyList() // Clear the list before fetching
            projectDao.getProjectsByStatus(status).collect { projectList ->
                _projects.value = projectList
            }
        }
    }

    fun getProjectsByStatus(status: String) {
        currentStatus = status
        loadProjects(status)
    }

    fun addProject(name: String, description: String, moreDescription: String, status: String) {
        viewModelScope.launch {
            val project = Project(
                name = name,
                description = description,
                moreDescription = moreDescription,
                status = status
            )
            projectDao.insertProject(project)
            loadProjects(currentStatus) // Reload based on current status
        }
    }

    fun getAllProjects() {
        viewModelScope.launch {
            _projects.value = emptyList() // Clear the list before fetching
            projectDao.getAllProjects().collect { projectList ->
                _projects.value = projectList
            }
        }
    }

    fun updateProject(project: Project) {
        viewModelScope.launch {
            projectDao.updateProject(project)
            loadProjects(currentStatus) // Reload based on current status
        }
    }

    fun deleteProject(project: Project) {
        viewModelScope.launch {
            projectDao.deleteProject(project)
            loadProjects(currentStatus) // Reload based on current status
        }
    }
}
