package com.projectdelta.medsapp.Util

import android.util.Log
import java.util.*
import java.util.concurrent.TimeUnit

fun fromMilliSecondsToMinutes( milli : Long ) : Long = TimeUnit.MINUTES.convert(milli , TimeUnit.MILLISECONDS)

fun fromMinutesToMilliSeconds( min : Long ) : Long = TimeUnit.MILLISECONDS.convert(min , TimeUnit.MINUTES)

fun fromMilliSecondsToString( milli : Long ) : String{
	val _time = fromMilliSecondsToMinutes(milli)
	return "${ _time / 60 }:${ _time % 60 }"
}

fun getDate() : Int{
	val c = Calendar.getInstance()
	return c.get(Calendar.DAY_OF_MONTH)
}