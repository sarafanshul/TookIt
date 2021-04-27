package com.projectdelta.medsapp.Constant

import com.projectdelta.medsapp.Data.UserData
import java.util.*

const val DATABASE_NAME = "user-db"

var pendingIntentRequestCode : Int = 0 // increment pendingIntentRequestCode for new pending intent

val DAY_VALUE = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1

const val NOTIFICATION_INTERVAL = 60L

const val CHANNEL_ID = "channelID"

const val CHANNEL_NAME = "channelNAME"


private var __id = 0
private var _size = 7 // change to available colors
val PREPOPULATE_DATA_EX = mutableListOf(
    UserData("Sunday",     mutableListOf( Pair("Abacavir"  , 17300000L) , Pair("dolutegravir" , 23400000L ), Pair("lamivudine" , 31420000L) , Pair("Acyclovir" , 78400000L))  ,  ( __id++ ) ),
    UserData("Monday",     mutableListOf( Pair("Abacavir"  , 17300000L) , Pair("dolutegravir" , 23400000L ), Pair("lamivudine" , 31420000L) , Pair("Acyclovir" , 78400000L))  ,  ( __id++ ) ),
    UserData("Tuesday",    mutableListOf( Pair("Abacavir"  , 17300000L) , Pair("dolutegravir" , 23400000L ), Pair("lamivudine" , 31420000L) , Pair("Acyclovir" , 78400000L))  ,  ( __id++ ) ),
    UserData("Wednesday",  mutableListOf( Pair("Metformin" , 17300000L) , Pair("Amlodipine"   , 23400000L ), Pair("Metoprolol" , 31420000L) , Pair("Omeprazole", 78400000L) ) ,  ( __id++ ) ),
    UserData("Thursday",   mutableListOf( Pair("Abacavir"  , 17300000L) , Pair("dolutegravir" , 23400000L ), Pair("lamivudine" , 31420000L) , Pair("Acyclovir" , 78400000L))  ,  ( __id++ ) ),
    UserData("Friday",     mutableListOf( Pair("Abacavir"  , 17300000L) , Pair("dolutegravir" , 23400000L ), Pair("lamivudine" , 31420000L) , Pair("Acyclovir" , 78400000L))  ,  ( __id++ ) ),
    UserData("Saturday",   mutableListOf( Pair("Abacavir"  , 17300000L) , Pair("dolutegravir" , 23400000L ), Pair("lamivudine" , 31420000L) , Pair("Acyclovir" , 78400000L))  ,  ( __id++ ) )
)

fun createPrePopulateData() : List<UserData>{
    return PREPOPULATE_DATA_EX
}


val DAYS = arrayOf(
    "Sunday",
    "Monday",
    "Tuesday",
    "Wednesday",
    "Thursday",
    "Friday",
    "Saturday"
)
