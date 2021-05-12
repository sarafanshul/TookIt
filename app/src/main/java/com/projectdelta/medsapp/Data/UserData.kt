package com.projectdelta.medsapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.projectdelta.medsapp.util.Converters
import java.io.Serializable

private var __id : Int = 0

@Entity
data class UserData (
    var day : String ,

    @TypeConverters( Converters::class )
    val list : MutableList<Pair<String , Long>> = mutableListOf() ,

    @PrimaryKey
    var id : Int = __id++

) : Serializable