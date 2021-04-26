package com.projectdelta.medsapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.projectdelta.medsapp.Data.AppDatabase
import com.projectdelta.medsapp.Data.UserData
import com.projectdelta.medsapp.Data.UserDataDao
import com.projectdelta.medsapp.Repository.UserDataRepository
import kotlinx.coroutines.GlobalScope
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
        GlobalScope.launch {
            repository.insertOrUpdate( data )
        }
    }

    fun update( data : UserData ){
        GlobalScope.launch {
            repository.update( data )
        }
    }

}