package com.projectdelta.medsapp.Data

import android.content.Context
import android.widget.Toast
import com.projectdelta.medsapp.Constant.PREPOPULATE_DATA
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object UserDatabaseManager {

	fun deleteAllInstances( context: Context , name : String , time : Long ){
		val DB = AppDatabase.getInstance(context).userDataDao()
		val job = GlobalScope.launch {
			val database = DB.getDatabase()
			for( i in database.indices ){
				if( database[i].list.removeAll { it.first == name && it.second == time } )
					DB.updateData( database[i] ) // update only if removed
			}
		}
		GlobalScope.launch { job.join() }
	}

	fun nukeDatabase(secondaryContext: Context){
		val DB = AppDatabase.getInstance(secondaryContext).userDataDao()

		GlobalScope.launch {
			PREPOPULATE_DATA.forEach {
				DB.insertOrUpdate(it)
			}
		}
		Toast.makeText( secondaryContext , "All data has been removed !" , Toast.LENGTH_SHORT ).show()
	}

}