package com.cairocart.di

import androidx.lifecycle.ViewModelProvider
import com.cairocart.ui.category.CategoryViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
class ViewModelModule {

    @Provides
    @Singleton
    fun provideCategoryViewModelProvider(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory =
        viewModelFactory

}