package com.example.domain.di

import com.example.domain.interactors.login.GetAuthTokenUseCase
import com.example.domain.interactors.login.GetAuthTokenUseCaseImpl
import com.example.domain.interactors.login.GetSessionIdUseCase
import com.example.domain.interactors.login.GetSessionIdUseCaseImpl
import org.koin.dsl.module

val interactsModule = module {
    factory<GetAuthTokenUseCase> { GetAuthTokenUseCaseImpl(authRepository = get()) }
    factory<GetSessionIdUseCase> { GetSessionIdUseCaseImpl(authRepository = get()) }
}