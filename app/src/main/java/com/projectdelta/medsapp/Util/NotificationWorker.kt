package com.projectdelta.medsapp.Util

import android.app.Notification
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.projectdelta.medsapp.Constant.DAY_VALUE
import com.projectdelta.medsapp.Data.AppDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class NotificationWorker(appContext: Context, workerParams: WorkerParameters):
		Worker(appContext, workerParams) {

	override fun doWork(): Result {
		Log.d( "WorkManager|Intent|Start" , "TEST-15min" )
		val job = GlobalScope.launch {

			val data = AppDatabase.getInstance(applicationContext).userDataDao().getToday(DAY_VALUE)
			val HOUR : Long = 3600000
			val calender = Calendar.getInstance()
			val offset = calender.get( Calendar.ZONE_OFFSET ) + calender.get( Calendar.DST_OFFSET )
			val sinceMidnight = ( calender.timeInMillis + offset ) % (24 * 60 * 60 * 1000)
			NotificationUtil.data = data.list.filter { it.second >= sinceMidnight && it.second <= sinceMidnight + HOUR*3L }

			if( data.list.size == 0 ) return@launch // no null notifications

			NotificationUtil.newNotification( applicationContext )
		}

		GlobalScope.launch { job.join() }

		return Result.success()
	}


}