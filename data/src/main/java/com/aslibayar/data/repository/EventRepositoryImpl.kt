package com.aslibayar.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.aslibayar.domain.model.Event
import com.aslibayar.domain.repository.EventRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : EventRepository {

    override fun getEventsForDay(dayStartMillis: Long, dayEndMillis: Long): Flow<List<Event>> =
        flow {
            try {
                val events = firestore.collection("events")
                    .whereGreaterThanOrEqualTo("startTimeMillis", dayStartMillis)
                    .whereLessThanOrEqualTo("startTimeMillis", dayEndMillis)
                    .get()
                    .await()
                    .toObjects(Event::class.java)
                emit(events)
            } catch (e: Exception) {
                emit(emptyList())
            }
        }

    override fun getEventsForMonth(
        monthStartMillis: Long,
        monthEndMillis: Long
    ): Flow<List<Event>> = flow {
        try {
            val events = firestore.collection("events")
                .whereGreaterThanOrEqualTo("startTimeMillis", monthStartMillis)
                .whereLessThanOrEqualTo("startTimeMillis", monthEndMillis)
                .get()
                .await()
                .toObjects(Event::class.java)
            emit(events)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getEvent(id: String): Result<Event> = try {
        val event = firestore.collection("events")
            .document(id)
            .get()
            .await()
            .toObject(Event::class.java) ?: throw Exception("Event not found")
        Result.success(event)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun addEvent(event: Event): Result<String> = try {
        val documentRef = firestore.collection("events").document()
        val eventWithId = event.copy(id = documentRef.id)
        documentRef.set(eventWithId).await()
        Result.success(documentRef.id)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateEvent(event: Event): Result<Unit> = try {
        firestore.collection("events")
            .document(event.id)
            .set(event.copy(updatedAt = System.currentTimeMillis()))
            .await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteEvent(id: String): Result<Unit> = try {
        firestore.collection("events")
            .document(id)
            .delete()
            .await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun searchEvents(query: String): Flow<List<Event>> = flow {
        try {
            val events = firestore.collection("events")
                .whereGreaterThanOrEqualTo("title", query)
                .whereLessThanOrEqualTo("title", query + '\uf8ff')
                .get()
                .await()
                .toObjects(Event::class.java)
            emit(events)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
}