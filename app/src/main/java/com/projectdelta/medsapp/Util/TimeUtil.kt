package com.projectdelta.medsapp.util


import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun fromMilliSecondsToMinutes( milli : Long ) : Long = TimeUnit.MINUTES.convert(milli , TimeUnit.MILLISECONDS)

fun fromMinutesToMilliSeconds( min : Long ) : Long = TimeUnit.MILLISECONDS.convert(min , TimeUnit.MINUTES)

fun fromMilliSecondsToString( milli : Long ) : String{
	val _time = fromMilliSecondsToMinutes(milli)
	return "${ if(_time / 60 < 10) "0"+(_time / 60).toString() else _time / 60 }:${ if(_time % 60 < 10) "0" + (_time % 60).toString() else _time %60 }"
}

fun getDate() : Int{
	val c = Calendar.getInstance()
	return c.get(Calendar.DAY_OF_MONTH)
}

fun getMonth( month : Int ) = DateFormatSymbols().months[ month - 1 ]

fun getMonthDateOffset(offset : Int) : Pair<String , String> {
	val c = Calendar.getInstance()
	val sdf_month = SimpleDateFormat("MM")
	val sdf_day = SimpleDateFormat("dd")
	c.add(Calendar.DAY_OF_YEAR , offset)
	return Pair( getMonth(sdf_month.format( Date( c.timeInMillis ) ).toInt()) , sdf_day.format( Date( c.timeInMillis ) ) )

}