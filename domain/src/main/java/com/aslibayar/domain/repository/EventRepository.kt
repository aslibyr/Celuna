package com.aslibayar.domain.repository

import com.aslibayar.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getEventsForDay(dayStartMillis: Long, dayEndMillis: Long): Flow<List<Event>>
    fun getEventsForMonth(monthStartMillis: Long, monthEndMillis: Long): Flow<List<Event>>
    suspend fun getEvent(id: String): Result<Event>
    suspend fun addEvent(event: Event): Result<String>
    suspend fun updateEvent(event: Event): Result<Unit>
    suspend fun deleteEvent(id: String): Result<Unit>
    suspend fun searchEvents(query: String): Flow<List<Event>>
} 