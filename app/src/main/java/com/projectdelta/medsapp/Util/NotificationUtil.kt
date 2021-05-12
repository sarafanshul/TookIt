package com.projectdelta.medsapp.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.projectdelta.medsapp.constant.CHANNEL_ID
import com.projectdelta.medsapp.constant.CHANNEL_NAME
import com.projectdelta.medsapp.constant.pendingIntentRequestCode
import com.projectdelta.medsapp.MainActivity
import com.projectdelta.medsapp.R
import java.util.*
import java.util.concurrent.TimeUnit

object NotificationUtil {

	val notification_icon = R.drawable.ic_twotone_medication_24
//	var data = emptyList<Pair< String , Long >>()

	fun createNotificationChannel(context : Context){
		val channel = NotificationChannel( CHANNEL_ID , CHANNEL_NAME , NotificationManager.IMPORTANCE_HIGH ).apply {
			enableLights(true)
		}
		val manager = context.getSystemService( Context.NOTIFICATION_SERVICE ) as NotificationManager
		manager.createNotificationChannel( channel )
	}

	fun newNotification( context_worker: Context , data : List<Pair<String , Long>> , NOTIFICATION_ID : Int = 1 ){
		if( data.size == 0 ) return  // don't fire for empty tasks

		val calender = Calendar.getInstance()
		val offset = calender.get( Calendar.ZONE_OFFSET ) + calender.get( Calendar.DST_OFFSET )
		val sinceMidnight = ( calender.timeInMillis + offset ) % (24 * 60 * 60 * 1000)

		val intent = Intent(context_worker, MainActivity::class.java).apply {
			flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
		}
		var inboxData = NotificationCompat.InboxStyle()
		data.forEach { inboxData.addLine("${it.first} at ${ fromMilliSecondsToString(it.second) }" ) }
		val _title = "Time for your meds!"
		val subText = "${data.first().first} in ${ fromMilliSecondsToString( data.first().second - sinceMidnight ) }"
		val pendingIntent: PendingIntent = PendingIntent.getActivity(context_worker, pendingIntentRequestCode++, intent, 0) // increment pendingIntentRequestCode for new pending intent

		val builder = NotificationCompat.Builder( context_worker , CHANNEL_ID ).apply{
			setSmallIcon( notification_icon )
			setContentTitle(_title)
			setContentText(subText)
			setStyle( inboxData )
			setContentIntent(pendingIntent)
			setAutoCancel(true)
			setPriority( NotificationCompat.PRIORITY_HIGH )
		}

		with(NotificationManagerCompat.from(context_worker)) {
			// notificationId is a unique int for each notification that you must define
			notify( NOTIFICATION_ID , builder.build())
		}
	}

	 fun startNotifications( context : Context , tag : String , interval : Long ){

//		 val notificationWorkRequest = PeriodicWorkRequestBuilder<NotificationWorker>(interval , TimeUnit.MILLISECONDS)
		 val notificationWorkRequest = PeriodicWorkRequestBuilder<NotificationWorker>(15 , TimeUnit.MINUTES)
			.addTag(tag)
			.build()
		 WorkManager.getInstance(context).enqueueUniquePeriodicWork(
			 tag,
			 ExistingPeriodicWorkPolicy.REPLACE,
			  notificationWorkRequest
		)
	}

	 fun stopNotifications( context : Context , tag : String ){
		WorkManager.getInstance().cancelAllWorkByTag(tag)
		WorkManager.getInstance().cancelAllWorkByTag(tag)
	}
}