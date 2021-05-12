package com.projectdelta.medsapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.projectdelta.medsapp.data.AppDatabase
import com.projectdelta.medsapp.data.UserData
import com.projectdelta.medsapp.data.UserDataDao
import com.projectdelta.medsapp.repository.UserDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel( application: Application ) : AndroidViewModel( application ) {

    private val repository : UserDataRepository
    val getAll : LiveData<List<UserData>>
    val getAllByOrder : LiveData<List<UserData>>
    val getAllExceptTodayByOrder : LiveData<List<UserData>>
    val today : LiveData<UserData>

    init {
        val usedDataDao : UserDataDao = AppDatabase.getInstance( application ).userDataDao()
        repository = UserDataRepository( usedDataDao )
        getAll = repository.getAll
        getAllByOrder = repository.getAllByOrder
        getAllExceptTodayByOrder = repository.getAllExceptTodayByOrder
        today = repository.today
    }

    fun insertOrUpdate( data : UserData ){
        viewModelScope.launch ( Dispatchers.IO ) {
            repository.insertOrUpdate( data )
        }
    }

    fun update( data : UserData ){
        viewModelScope.launch ( Dispatchers.IO ) {
            repository.update( data )
        }
    }

}