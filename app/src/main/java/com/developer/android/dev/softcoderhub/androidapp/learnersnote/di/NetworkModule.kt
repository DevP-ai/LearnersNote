package com.developer.android.dev.softcoderhub.androidapp.learnersnote.di

import com.developer.android.dev.softcoderhub.androidapp.learnersnote.api.AuthInterceptor
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.api.NotesAPI
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.api.UserAPI
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.utils.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton
import javax.xml.transform.OutputKeys

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitBuilder():Retrofit.Builder{
        return  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor):OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor).build()
    }
    @Singleton
    @Provides
    fun provideUserAPI(retrofitBuilder: Retrofit.Builder):UserAPI{
        return  retrofitBuilder
            .build().create(UserAPI::class.java)
    }


    @Singleton
    @Provides
    fun provideNoteAPI(retrofitBuilder: Retrofit.Builder,okHttpClient: OkHttpClient):NotesAPI{
        return retrofitBuilder
            .client(okHttpClient)
            .build().create(NotesAPI::class.java)
    }
}