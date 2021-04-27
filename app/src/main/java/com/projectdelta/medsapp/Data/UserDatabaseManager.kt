package com.projectdelta.medsapp.Data

import android.content.Context
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object UserDatabaseManager {

	lateinit var context: Context

	fun deleteAllInstances( name : String , time : Long ){

		GlobalScope.launch {
			val data = AppDatabase.getInstance(context).userDataDao().getDatabase()
			var newData : MutableList<UserData>
			for( i in data.indices ){
				 val ud =  UserData( data[i].day , mutableListOf() , mutableListOf() , data[i].id )
				// convert to pairs then comeback !
			}
		}

	}

}