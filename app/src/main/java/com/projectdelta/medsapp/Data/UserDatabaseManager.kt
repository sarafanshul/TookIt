package com.projectdelta.medsapp.data

import android.content.Context
import android.util.Log
import com.projectdelta.medsapp.constant.DATA_SIZE
import com.projectdelta.medsapp.constant.createPrePopulateData
import kotlinx.coroutines.*

object UserDatabaseManager {

	fun deleteAllInstances( context: Context , name : String , time : Long ){
		val DB = AppDatabase.getInstance(context).userDataDao()
		GlobalScope.launch {
			IntRange(0 , DATA_SIZE).map{
				async(Dispatchers.IO) {
					val dataX = DB.getDataById(it)
					if(dataX.list.removeAll{ x -> x.first == name && x.second == time })
						DB.updateData( dataX )
				}
			}.awaitAll()
		}
	}

	fun updateMultipleData(context: Context , selectedDays : List<Int> , name : String , time : Long){
		val DB = AppDatabase.getInstance(context).userDataDao()
		GlobalScope.launch {
			selectedDays.map{ id ->
				async( Dispatchers.IO ) {
					val dataX = DB.getDataById(id)
					var needToDO = true
					dataX.list.forEach {
						if (it.first == name && it.second == time) needToDO = false
					}
					if( needToDO ){
						var idx = dataX.list.size;
						for (i in dataX.list.indices) {
							if (time <= dataX.list[i].second) {
								idx = i; break; }
						}
						dataX.list.add( idx , Pair( name , time ) )
						DB.updateData( dataX )
					}
				}
			}.awaitAll()
		}
	}

	fun nukeDatabase(secondaryContext: Context) : Boolean{
		val DB = AppDatabase.getInstance(secondaryContext).userDataDao()
		try{
			GlobalScope.launch {
				val job = launch(Dispatchers.IO) {
					createPrePopulateData().forEach {
						DB.insertOrUpdate(it)
					}
				}
				job.join()
			}
			return true
		}
		catch (e : Exception ){
			Log.e( "Fatal Exception during nukeDatabase" , e.stackTrace.toString() )
		}
		return false
	}

}