package com.aslibayar.domain.usecase.event

import com.aslibayar.domain.model.Event
import com.aslibayar.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventsForDayUseCase @Inject constructor(
    private val repository: EventRepository
) {
    operator fun invoke(dayStartMillis: Long, dayEndMillis: Long): Flow<List<Event>> {
        return repository.getEventsForDay(dayStartMillis, dayEndMillis)
    }

    companion object {
        /**
         * Günün başlangıç zamanını hesaplar (00:00:00.000)
         */
        fun getDayStartMillis(timeMillis: Long): Long {
            return timeMillis - (timeMillis % 86400000) // 24*60*60*1000
        }

        /**
         * Günün bitiş zamanını hesaplar (23:59:59.999)
         */
        fun getDayEndMillis(timeMillis: Long): Long {
            return getDayStartMillis(timeMillis) + 86399999 // (24*60*60*1000) - 1
        }
    }
}