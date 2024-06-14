package com.example.movieapplication.di

import android.app.Application
import android.provider.MediaStore.Audio.Media
import androidx.room.Database
import androidx.room.Room
import com.example.movieapplication.main.data.local.genres.GenresDatabase
import com.example.movieapplication.main.data.local.media.MediaDatabase
import com.example.movieapplication.main.data.remote.api.GenresApi
import com.example.movieapplication.main.data.remote.api.MediaApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun providesMediaApi(): MediaApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(MediaApi.BASE_URL)
            .client(client)
            .build()
            .create(MediaApi::class.java)
    }

    @Provides
    @Singleton
    fun providesGenresApi(): GenresApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(GenresApi.BASE_URL)
            .client(client)
            .build()
            .create(GenresApi::class.java)
    }

    @Provides
    @Singleton
    fun providesMediaDatabase(app: Application): MediaDatabase{
        return Room.databaseBuilder(
            app,
            MediaDatabase::class.java,
            "mediadb.db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesGenresDatabase(app : Application):GenresDatabase{
        return Room.databaseBuilder(
            app,
            GenresDatabase::class.java,
            "genresdb.db"
        ).build()
    }


}