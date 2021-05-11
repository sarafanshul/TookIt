package com.projectdelta.medsapp.Constant

import com.projectdelta.medsapp.Data.UserData
import java.util.*

const val DATABASE_NAME = "user-db"

var pendingIntentRequestCode : Int = 0 // increment pendingIntentRequestCode for new pending intent

val DAY_VALUE = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1

const val NOTIFICATION_INTERVAL = 60L

const val CHANNEL_ID = "channelID"

const val CHANNEL_NAME = "channelNAME"

val DATA_SIZE : Int = 6 // total size , ids

val PREPOPULATE_DATA_EX = mutableListOf(
    UserData("Sunday",     mutableListOf( Pair("Abacavir"  , 17300000L) , Pair("Dolutegravir" , 23400000L ), Pair("Lamivudine" , 31420000L) , Pair("Acyclovir" , 78400000L))  ,  ( 0 ) ),
    UserData("Monday",     mutableListOf( Pair("Abacavir"  , 17300000L) , Pair("Dolutegravir" , 23400000L ), Pair("Lamivudine" , 31420000L) , Pair("Acyclovir" , 78400000L))  ,  ( 1 ) ),
    UserData("Tuesday",    mutableListOf( Pair("Abacavir"  , 17300000L) , Pair("Dolutegravir" , 23400000L ), Pair("Lamivudine" , 31420000L) , Pair("Acyclovir" , 78400000L))  ,  ( 2 ) ),
    UserData("Wednesday",  mutableListOf( Pair("Metformin" , 17300000L) , Pair("Amlodipine"   , 23400000L ), Pair("Metoprolol" , 31420000L) , Pair("Omeprazole", 78400000L) ) ,  ( 3 ) ),
    UserData("Thursday",   mutableListOf( Pair("Abacavir"  , 17300000L) , Pair("Dolutegravir" , 23400000L ), Pair("Lamivudine" , 31420000L) , Pair("Acyclovir" , 78400000L))  ,  ( 4 ) ),
    UserData("Friday",     mutableListOf( Pair("Abacavir"  , 17300000L) , Pair("Dolutegravir" , 23400000L ), Pair("Lamivudine" , 31420000L) , Pair("Acyclovir" , 78400000L))  ,  ( 5 ) ),
    UserData("Saturday",   mutableListOf( Pair("Abacavir"  , 17300000L) , Pair("Dolutegravir" , 23400000L ), Pair("Lamivudine" , 31420000L) , Pair("Acyclovir" , 78400000L))  ,  ( 6 ) )
)

val PREPOPULATE_DATA = mutableListOf(
    UserData("Sunday",     mutableListOf( )  ,  ( 0 ) ),
    UserData("Monday",     mutableListOf( )  ,  ( 1 ) ),
    UserData("Tuesday",    mutableListOf( )  ,  ( 2 ) ),
    UserData("Wednesday",  mutableListOf( )  ,  ( 3 ) ),
    UserData("Thursday",   mutableListOf( )  ,  ( 4 ) ),
    UserData("Friday",     mutableListOf( )  ,  ( 5 ) ),
    UserData("Saturday",   mutableListOf( )  ,  ( 6 ) )
)

fun createPrePopulateData() : List<UserData>{
    return PREPOPULATE_DATA_EX // use EX for debugging
}

val WEEKDAYS = arrayOf(
    "Sunday",
    "Monday",
    "Tuesday",
    "Wednesday",
    "Thursday",
    "Friday",
    "Saturday"
)
