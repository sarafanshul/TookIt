package com.projectdelta.medsapp.repository

import androidx.lifecycle.LiveData
import com.projectdelta.medsapp.constant.DAY_VALUE
import com.projectdelta.medsapp.data.UserData
import com.projectdelta.medsapp.data.UserDataDao

class UserDataRepository( private val userDataDao: UserDataDao ) {

    val getAll : LiveData<List<UserData>> = userDataDao.getAll()

    val getAllByOrder : LiveData<List<UserData>> = userDataDao.getAllByOrder( DAY_VALUE ) // in custom order

    val getAllExceptTodayByOrder : LiveData<List<UserData>> = userDataDao.getAllExceptTodayByOrder( DAY_VALUE ) // in custom order except today

    val today : LiveData<UserData> = userDataDao.today( DAY_VALUE )

    suspend fun insertOrUpdate( data : UserData ){
        userDataDao.insertOrUpdate( data )
    }

    suspend fun update( data: UserData ){
        userDataDao.updateData( data )
    }

    fun getDataById( id : Int ) : LiveData<UserData>{
       return userDataDao.getLiveDataById(id)
    }

    suspend fun deleteData( data: UserData ){
        userDataDao.deleteData( data )
    }

    suspend fun getDataByIdMainThread( id : Int ) : UserData{
        return userDataDao.getDataByIdMainThread( id )
    }

}