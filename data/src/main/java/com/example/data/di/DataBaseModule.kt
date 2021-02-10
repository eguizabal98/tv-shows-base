package com.example.data.di

import androidx.room.Room
import com.example.data.database.DataBase
import com.example.data.paging.ItemBoundaryCallBack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

const val DB_NAME = "TvShowsDb"

val dataBaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), DataBase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
    factory { get<DataBase>().tvShowDao() }
    factory { get<DataBase>().favoriteShowDao() }
    factory { get<DataBase>().accountDao() }

    single {
        ItemBoundaryCallBack(tvShowsAPI = get(), dataBase = get(), scope = get())
    }

    factory { CoroutineScope(Dispatchers.IO) }
}