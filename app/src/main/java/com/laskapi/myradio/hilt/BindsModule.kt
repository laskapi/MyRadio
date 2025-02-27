package com.laskapi.myradio.hilt

import com.laskapi.myradio.data.StationsDataSource
import com.laskapi.myradio.data.StationsRetrofitDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {
    @Binds
    abstract fun bindStationsDataSource(stationsRetrofitDataSource: StationsRetrofitDataSource):
            StationsDataSource
}