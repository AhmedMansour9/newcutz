package com.cairocart.di

import android.app.Application
import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import com.cairocart.App
import com.cairocart.data.DataCenterImpl
import com.cairocart.data.DataCenterManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ApplicationModule {

    @Singleton
    @Provides
    fun provideDataCenterManger(dataCenterImpl: DataCenterImpl): DataCenterManager = dataCenterImpl

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.createDataStore("store_cart_app")
    }


}