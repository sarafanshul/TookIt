package com.projectdelta.medsapp.util

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.projectdelta.medsapp.constant.DAY_VALUE
import com.projectdelta.medsapp.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*

class NotificationWorker(appContext: Context, workerParams: WorkerParameters):
		Worker(appContext, workerParams) {

	override fun doWork(): Result {

		Log.d( "WorkManager|Intent|Start" , "TEST-15min" )

		val HOUR: Long = 3600000
		val calender = Calendar.getInstance()
		val offset = calender.get(Calendar.ZONE_OFFSET) + calender.get(Calendar.DST_OFFSET)
		val sinceMidnight = (calender.timeInMillis + offset) % (24 * 60 * 60 * 1000)

		GlobalScope.launch {

			val job = launch {
				var _data = async( Dispatchers.IO ) {
					AppDatabase.getInstance(applicationContext).userDataDao().getToday(DAY_VALUE)
				}

				val notificationData =
					_data.await().list.filter { it.second >= sinceMidnight && it.second <= sinceMidnight + HOUR * 3L }

				if (notificationData.isEmpty()) return@launch // no null notifications

				NotificationUtil.newNotification(applicationContext, notificationData, 1)
			}

			job.join()
		}

		return Result.success()
	}


}