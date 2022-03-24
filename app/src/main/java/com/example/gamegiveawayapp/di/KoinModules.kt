package com.example.gamegiveawayapp.di

import android.content.Context
import androidx.room.Room
import com.example.gamegiveawayapp.database.GiveawayDAO
import com.example.gamegiveawayapp.database.GiveawayDatabase
import com.example.gamegiveawayapp.network.*
import com.example.gamegiveawayapp.viewmodel.GiveawaysViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val networkModule = module {

    fun providesLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(30,TimeUnit.SECONDS)
            .readTimeout(30,TimeUnit.SECONDS)
            .writeTimeout(30,TimeUnit.SECONDS)
            .build()



    fun providesNetworkService(okHttpClient: OkHttpClient): GiveawayService =
        Retrofit.Builder()
            .baseUrl(GiveawayService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(GiveawayService::class.java)


    fun providesGiveawayRepo(giveawayService: GiveawayService) : GiveawaysRepository =
        GiveawaysRepositoryImpl(giveawayService)

    single { providesLoggingInterceptor() }
    single { providesOkHttpClient(get()) }
    single { providesNetworkService(get()) }
    single { providesGiveawayRepo(get()) }

}

val applicationModule = module {
    fun providesGiveawayDatabase(context: Context): GiveawayDatabase =
        Room.databaseBuilder(
            context,
            GiveawayDatabase::class.java,
            "giveaways-db"
        ).build()

    fun providesGiveawaysDao(giveawayDatabase: GiveawayDatabase): GiveawayDAO =
        giveawayDatabase.getGiveawayDao()

    fun providesDatabaseRepository(databaseDAO: GiveawayDAO): DatabaseRepository =
        DatabaseRepositoryImpl(databaseDAO)

    single { providesGiveawayDatabase(androidContext()) }
    single {providesDatabaseRepository(get())}
    single {providesGiveawaysDao(get())}

}

val viewModelModule = module{
    viewModel {GiveawaysViewModel(get(), get())}
}