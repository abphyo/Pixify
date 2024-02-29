package com.biho.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.biho.pixify.core.model.danbooru.repository.UserRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun databaseModule() = module {
    single { provideDatabase(context = get()) }
    single { get<PixifyDatabase>().userDao() }
    singleOf(::UserRepoImpl) { bind<UserRepository>() }
}

fun provideDatabase(context: Context): PixifyDatabase {
    return Room
        .databaseBuilder(
            context = context,
            klass = PixifyDatabase::class.java,
            name = "Pixify"
        )
        .addCallback(
            callback = object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    db.execSQL("insert into users (id, name, blacklist_tags, favourite_tags) values (0, 'Lucker', '[]', '[]')")
                }
            }
        )
        .fallbackToDestructiveMigration()
        .build()
}