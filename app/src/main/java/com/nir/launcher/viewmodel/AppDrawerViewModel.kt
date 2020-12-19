package com.nir.launcher.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nir.launcher.data.IAppDrawerRepository
import com.nir.launcher.model.AppData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppDrawerViewModel(
    private val appDrawerRepository: IAppDrawerRepository
) : ViewModel() {

    val appLiveData = MutableLiveData<List<AppData>>()

    fun getAppLaunchers() {
        viewModelScope.launch(Dispatchers.IO) {
           val response = appDrawerRepository.getAppLaunchers()
            appLiveData.postValue(response)
        }
    }

}