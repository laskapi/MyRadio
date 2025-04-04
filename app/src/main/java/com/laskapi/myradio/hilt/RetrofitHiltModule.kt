package com.laskapi.myradio.hilt

import android.content.Context
import androidx.room.Room
import com.laskapi.myradio.data.MirrorsProvider
import com.laskapi.myradio.data.StationsRetrofitService
import com.laskapi.myradio.data.StationsRetrofitDataSource
import com.laskapi.myradio.room.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
const val DB_NAME="radioDatabase"

@Module
@InstallIn(SingletonComponent::class)
object RetrofitHiltModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext ctx: Context): Database
        {
          //  ctx.deleteDatabase(DB_NAME)
           return Room.databaseBuilder(ctx, Database::class.java, DB_NAME).build()
        }
    @Singleton
    @Provides
    fun provideMirrorDao(db: Database) = db.getMirrorDao()

    @Singleton
    @Provides
    fun provideFavoritesDao(db:Database)=db.getFavoriteDao()

   /* @Provides
    @Named("mirrorUrl")
    fun provideMirrorUrl(mirrorsRepository: MirrorsRepository): String {
        return mirrorsRepository.getMirror()
    }*/


    @Provides
    fun provideStationsRetrofitService(mirrorsProvider: MirrorsProvider):

            StationsRetrofitService {
        return Retrofit.Builder()
            .baseUrl(mirrorsProvider.getMirror()?.address)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StationsRetrofitService::class.java)

    }
@Singleton
    @Provides
    fun provideStationsDataSource(stationsService: StationsRetrofitService) =
        StationsRetrofitDataSource(stationsService, Dispatchers.IO)

}