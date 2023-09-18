package com.example.puconnect.presentation.ViewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.puconnect.domain.model.GenreWithSkills
import com.example.puconnect.domain.model.Skill
import com.example.puconnect.domain.use_cases.SkillsUseCases.SkillsUseCases
import com.example.puconnect.util.Response
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SkillsViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val skillsUseCases: SkillsUseCases
) : ViewModel() {

    private val _skills = mutableStateOf<Response<List<GenreWithSkills>>>(Response.Loading)
    val skills: State<Response<List<GenreWithSkills>>> = _skills

    fun getSkills() {
        viewModelScope.launch {
            skillsUseCases.getSkills().collect {
                _skills.value = it
            }
        }
    }

    private val _uploadSkills = mutableStateOf<Response<Boolean>>(Response.Loading)
    val uploadSkills: State<Response<Boolean>> = _uploadSkills
    fun setSkills(skillsList: List<Skill>, userId: String) {
        viewModelScope.launch {
            skillsUseCases.setSkills(skillsList,userId).collect {
                _uploadSkills.value = it
            }
        }
    }
}