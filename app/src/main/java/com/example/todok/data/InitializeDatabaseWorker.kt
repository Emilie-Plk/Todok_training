package com.example.todok.data

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todok.data.utils.fromJson
import com.example.todok.domain.CoroutineDispatcherProvider
import com.example.todok.domain.category.CategoryEntity
import com.example.todok.domain.insert.InsertCategoryUseCase
import com.google.gson.Gson
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.withContext

@HiltWorker
class InitializeDatabaseWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val insertCategoryUseCase: InsertCategoryUseCase,
    private val gson: Gson,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : CoroutineWorker(context, workerParams) {

    companion object {
        const val KEY_INPUT_DATA = "KEY_INPUT_DATA"
    }

    override suspend fun doWork(): Result = withContext(coroutineDispatcherProvider.io) {
        val entitiesAsJson = inputData.getString(KEY_INPUT_DATA)

        if (entitiesAsJson != null) {
            val categoryEntities = gson.fromJson<List<CategoryEntity>>(json = entitiesAsJson)

            if (categoryEntities != null) {
                categoryEntities.forEach { categoryEntity ->
                    insertCategoryUseCase.invoke(categoryEntity)
                }
                Result.success()
            } else {
                Log.e(javaClass.simpleName, "Gson can't parse categories : $entitiesAsJson")
                Result.failure()
            }
        } else {
            Log.e(
                javaClass.simpleName,
                "Failed to get data with key $KEY_INPUT_DATA from data: $inputData"
            )
            Result.failure()
        }
    }
}
