package com.projectdelta.medsapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.projectdelta.medsapp.Data.AppDatabase
import com.projectdelta.medsapp.Data.UserData
import com.projectdelta.medsapp.Data.UserDataDao
import com.projectdelta.medsapp.Repository.UserDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InfoViewModel( application: Application ) : AndroidViewModel( application ) {

    private val repository : UserDataRepository

    init {
        val userDataDao : UserDataDao = AppDatabase.getInstance(application).userDataDao()
        repository = UserDataRepository( userDataDao )
    }

    fun getDataById( id : Int ) : LiveData<UserData>{
        return repository.getDataById( id )
    }

    fun update(data : UserData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.update( data )
        }
    }

}