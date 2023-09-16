package com.example.puconnect.presentation.ViewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.puconnect.domain.model.GenreWithSkills
import com.example.puconnect.domain.use_cases.SkillsUseCases.SkillsUseCases
import com.example.puconnect.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SkillsViewModel @Inject constructor(
    private val skillsUseCases: SkillsUseCases
) :ViewModel() {

    private val _skills = mutableStateOf<Response<List<GenreWithSkills>?>>(Response.Success(null))
    val skills: State<Response<List<GenreWithSkills>?>> = _skills

    fun getSkills(){
        viewModelScope.launch {
            val data = skillsUseCases
            data.getSkills.invoke().collect(){

            }
//            skillsUseCases.getSkills
        }
    }
}