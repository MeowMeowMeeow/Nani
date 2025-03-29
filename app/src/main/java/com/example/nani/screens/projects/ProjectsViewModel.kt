package com.example.nani.screens.projects

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.nani.data.Project
import com.example.nani.network.data.ProjectDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProjectViewModel(application: Application) : AndroidViewModel(application) {

    private val projectDao = ProjectDatabase.getDatabase(application).projectDao()
    private val _projects = MutableStateFlow<List<Project>>(emptyList())
    val projects: StateFlow<List<Project>> = _projects


    fun getProjectsByStatus(status: String) {
        viewModelScope.launch {
            projectDao.getProjectsByStatus(status).collect { projectList ->
                _projects.value = projectList
            }
        }
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
            getProjectsByStatus(status)
        }
    }


    fun updateProject(project: Project) {
        viewModelScope.launch {
            projectDao.updateProject(project)
            getProjectsByStatus(project.status)
        }
    }


    fun deleteProject(project: Project) {
        viewModelScope.launch {
            projectDao.deleteProject(project)
            getProjectsByStatus(project.status)
        }
    }
}