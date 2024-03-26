package com.developer.android.dev.softcoderhub.androidapp.learnersnote.di

import com.developer.android.dev.softcoderhub.androidapp.learnersnote.api.UserAPI
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.utils.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit():Retrofit{
        return  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideUserAPI(retrofit: Retrofit):UserAPI{
        return  retrofit.create(UserAPI::class.java)
    }
}