package com.projectdelta.medsapp.Data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.projectdelta.medsapp.Util.Converters
import java.io.Serializable

private var __id : Int = 0

@Entity
data class UserData (
    val day : String ,

    @TypeConverters( Converters::class )
    val list : MutableList<String> = mutableListOf() ,

    @TypeConverters( Converters::class )
    val timeList : MutableList<Float> = mutableListOf() ,

    @PrimaryKey
    val id : Int = __id++

) : Serializable