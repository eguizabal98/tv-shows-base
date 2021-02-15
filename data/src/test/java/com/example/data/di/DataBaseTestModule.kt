package com.example.data.di

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.database.DataBase
import org.koin.dsl.module

val databaseTestModule = module {
    single { ApplicationProvider.getApplicationContext<Context>() }
    single {
        Room.inMemoryDatabaseBuilder(get(), DataBase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    factory { get<DataBase>().tvShowDao() }
    factory { get<DataBase>().favoriteShowDao() }
    factory { get<DataBase>().accountDao() }
    factory { get<DataBase>().detailsDao() }
    factory { get<DataBase>().creditsDao() }
    factory { get<DataBase>().seasonDao() }

}