package com.projectdelta.medsapp.service


import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.core.app.JobIntentService

class MyIntentService : JobIntentService() {

	companion object {
		private lateinit var instance: MyIntentService
		var isRunning : Boolean = false

		fun stopService(){
			isRunning = false
			instance.stopSelf()
		}
	}

	init {
		instance = this
	}

	override fun onHandleWork(intent: Intent) {
		onHandleIntent( intent )
	}

	private fun onHandleIntent( intent: Intent? ){

	}

}