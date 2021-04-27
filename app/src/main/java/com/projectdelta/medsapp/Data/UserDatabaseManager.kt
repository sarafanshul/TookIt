package com.projectdelta.medsapp.Data

import android.content.Context
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object UserDatabaseManager {

	lateinit var context: Context

	fun deleteAllInstances( name : String , time : Long ){
		val DB = AppDatabase.getInstance(context).userDataDao()
		GlobalScope.launch {
			val database = DB.getDatabase()
			for( i in database.indices ){
				database[i].list.removeAll { it.first == name && it.second == time }
				DB.updateData( database[i] )
			}
		}

	}

}