package com.projectdelta.medsapp.Data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDataDao {

    @Query( "SELECT * FROM UserData" )
    fun getAll(): LiveData<List<UserData>>

    @Query( "SELECT * FROM UserData" )
    fun getDatabase(): List<UserData>

    @Query( " SELECT * FROM ( SELECT * FROM UserData WHERE id >= :dayValue ORDER BY id ASC ) UNION ALL SELECT * FROM ( SELECT * FROM UserData WHERE id < :dayValue ORDER BY id ASC)" )
    fun getAllByOrder(dayValue : Int): LiveData< List< UserData > >

    @Query( "SELECT * FROM UserData WHERE id = :id " )
    fun getLiveDataById(id : Int) : LiveData<UserData>

    @Query( "SELECT * FROM UserData WHERE id = :id " )
    suspend fun getDataById(id : Int) : UserData

    @Query( "SELECT * FROM UserData WHERE id = :id " )
    suspend fun getDataByIdMainThread( id : Int) : UserData

    @Query("SELECT * FROM UserData WHERE id == :dayValue")
    fun today(dayValue : Int) : LiveData<UserData>

    @Query("SELECT * FROM UserData WHERE id == :dayValue")
    suspend fun getToday(dayValue : Int) : UserData

    @Query( " SELECT * FROM ( SELECT * FROM UserData WHERE id > :dayValue ORDER BY id ASC ) UNION ALL SELECT * FROM ( SELECT * FROM UserData WHERE id < :dayValue ORDER BY id ASC)" )
    fun getAllExceptTodayByOrder( dayValue: Int ) : LiveData< List<UserData> >

    @Delete
    suspend fun deleteData( data: UserData )

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    suspend fun insertOrUpdate( data: UserData)

    @Insert
    fun insertData(data: List<UserData>)

    @Update
    suspend fun updateData(userData: UserData)

}