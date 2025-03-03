package com.aslibayar.domain.usecase.event

import com.aslibayar.domain.model.Event
import com.aslibayar.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import javax.inject.Inject

class GetEventsForMonthUseCase @Inject constructor(
    private val repository: EventRepository
) {
    operator fun invoke(year: Int, month: Int): Flow<List<Event>> {
        val calendar = Calendar.getInstance()

        // Ayın başlangıcı
        calendar.set(year, month, 1, 0, 0, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val monthStartMillis = calendar.timeInMillis

        // Ayın sonu
        calendar.set(year, month, calendar.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val monthEndMillis = calendar.timeInMillis

        return repository.getEventsForMonth(monthStartMillis, monthEndMillis)
    }
} 