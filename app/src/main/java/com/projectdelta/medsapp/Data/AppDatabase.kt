package com.projectdelta.medsapp.Data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.projectdelta.medsapp.Constant.DATABASE_NAME
import com.projectdelta.medsapp.Constant.createPrePopulateData
import com.projectdelta.medsapp.Util.Converters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.concurrent.Executors


@Database(
    entities = [UserData::class] ,
    version = 1 ,
    exportSchema = false
)
@TypeConverters( Converters::class )
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDataDao() : UserDataDao

    companion object{
        // For Singleton instantiation
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if( tempInstance != null )
                return tempInstance

            synchronized(this){
                val instance = buildDatabase(context)
                INSTANCE = instance
                return instance
            }
        }

        private fun buildDatabase( context: Context ) : AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext ,
                AppDatabase::class.java ,
                DATABASE_NAME
            ).addCallback(object :  RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase){
                    super.onCreate(db)
                    // pre-populate data
                     // prepopulate works on a new thread so can return Null if database is not build while required
                    Executors.newSingleThreadExecutor().execute {
                        INSTANCE?.let {
                            it.userDataDao().insertData(createPrePopulateData())
                        }
                    }
                }
            }
            ).fallbackToDestructiveMigration() .build()
        }
    }

}