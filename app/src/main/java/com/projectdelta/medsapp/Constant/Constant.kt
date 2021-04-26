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
    UserData("Sunday",     mutableListOf( "Abacavir" , "dolutegravir " , "lamivudine" , "Acyclovir" ) , mutableListOf(1F , 3F , 4F , 6F , 10F )  , ( __id++ ) ),
    UserData("Monday",     mutableListOf( "Abacavir" , "dolutegravir " , "lamivudine" , "Acyclovir" ) , mutableListOf(1F , 3F , 4F , 6F , 10F )  , ( __id++ ) ),
    UserData("Tuesday",    mutableListOf( "Metformin" , "Amlodipine" , "Metoprolol" , "Omeprazole" )  , mutableListOf(1F , 3F , 4F , 6F , 10F )  , ( __id++ ) ),
    UserData("Wednesday",  mutableListOf( "Metformin" , "Amlodipine" , "Metoprolol" , "Omeprazole" )  , mutableListOf(1F , 3F , 4F , 6F , 10F )  , ( __id++ ) ),
    UserData("Thursday",   mutableListOf( "Abacavir" , "dolutegravir " , "lamivudine" , "Acyclovir" ) , mutableListOf(1F , 3F , 4F , 6F , 10F )  , ( __id++ ) ),
    UserData("Friday",     mutableListOf( "Abacavir" , "dolutegravir " , "lamivudine" , "Acyclovir" ) , mutableListOf(1F , 3F , 4F , 6F , 10F )  , ( __id++ ) ),
    UserData("Saturday",   mutableListOf( "Abacavir" , "dolutegravir " , "lamivudine" , "Acyclovir" ) , mutableListOf(1F , 3F , 4F , 6F , 10F )  , ( __id++ ) )
)

fun createPrePopulateData() : List<UserData>{
    return PREPOPULATE_DATA_EX
}
