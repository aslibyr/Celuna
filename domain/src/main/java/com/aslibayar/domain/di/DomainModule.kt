package com.aslibayar.domain.di

import com.aslibayar.domain.repository.AuthRepository
import com.aslibayar.domain.repository.EventRepository
import com.aslibayar.domain.usecase.auth.SignInUseCase
import com.aslibayar.domain.usecase.event.GetEventsForDayUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideSignInUseCase(
        repository: AuthRepository
    ): SignInUseCase = SignInUseCase(repository)

    @Provides
    @Singleton
    fun provideGetEventsForDayUseCase(
        repository: EventRepository
    ): GetEventsForDayUseCase = GetEventsForDayUseCase(repository)
} 