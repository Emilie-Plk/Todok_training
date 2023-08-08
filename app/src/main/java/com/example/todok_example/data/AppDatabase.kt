package com.example.todok_example.data

import android.app.Application
import androidx.core.content.res.ResourcesCompat
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.todok_example.R
import com.example.todok_example.data.dao.CategoryDao
import com.example.todok_example.data.dao.TodoDao
import com.example.todok_example.domain.category.CategoryEntity
import com.example.todok_example.domain.todo.TodoEntity
import com.google.gson.Gson

@Database(
    entities = [
        TodoEntity::class,
        CategoryEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getTodoDao(): TodoDao
    abstract fun getCategoryDao(): CategoryDao

    companion object {
        private const val DATABASE_NAME = "TodoK_database"

        fun create(
            application: Application,
            workManager: WorkManager,
            gson: Gson,
        ): AppDatabase {
            val builder = Room.databaseBuilder(
                application,
                AppDatabase::class.java,
                DATABASE_NAME
            )

            builder.addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    val entitiesAsJson = gson.toJson(
                        listOf(
                            CategoryEntity(
                                name = application.getString(R.string.personal_category),
                                colorInt = ResourcesCompat.getColor(
                                    application.resources,
                                    R.color.deep_red,
                                    null
                                )
                            ),
                            CategoryEntity(
                                name = application.getString(R.string.profesional_category),
                                colorInt = ResourcesCompat.getColor(
                                    application.resources,
                                    R.color.grayish_black,
                                    null
                                )
                            ),
                            CategoryEntity(
                                name = application.getString(R.string.familial_category),
                                colorInt = ResourcesCompat.getColor(
                                    application.resources,
                                    R.color.black,
                                    null
                                )
                            ),
                        )
                    )

                    workManager.enqueue(
                        OneTimeWorkRequestBuilder<InitializeDatabaseWorker>()
                            .setInputData(
                                workDataOf(
                                    InitializeDatabaseWorker.KEY_INPUT_DATA to entitiesAsJson
                                )
                            )
                            .build()
                    )
                }
            }
            )
            return builder.build()
        }
    }
}